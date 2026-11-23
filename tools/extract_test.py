#!/usr/bin/env python3
"""Extrai links e metadados dos sites e valida o contrato de cada plugin.

Rodado no GitHub Actions (validate.yml): para cada site faz
  home -> cards?, busca -> resultados?, detalhe -> metadados?, extracao -> link de midia?
e publica um relatorio markdown no job summary (+ JSON em artifact).
"""
import json, re, sys, urllib.request, urllib.parse

UA = {"User-Agent": "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Mobile Safari/537.36"}

def get(url, headers=None, timeout=25):
    req = urllib.request.Request(url, headers={**UA, **(headers or {})})
    with urllib.request.urlopen(req, timeout=timeout) as r:
        return r.status, r.read().decode("utf-8", "replace")

def count(html, sel_re):
    return len(re.findall(sel_re, html))

# contrato por site: (nome, mainUrl, probes)
SITES = [
    ("NetCine", "https://netcinexx.lat", [
        ("home", "{base}/", r'<div class="movie">'),
        ("busca", "{base}/?s=batman", r'<div class="movie">'),
        ("detalhe-filme", "{base}/tvshows/", r'<div class="movie">'),
    ], r'(?:hlsarchive\.php\?n=[^"\']+|https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("OverFlix", "https://overflix.cc", [
        ("home", "{base}/inicio", r'<article'),
        ("busca", "{base}/pesquisa?s=resident", r'<article'),
        ("detalhe-filme", "{base}/filme/resident-evil", r'fshd\.link/filme/\w+'),
    ], r'(?:fshd\.link/(?:filme|serie)/[\w/]+|https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("TopFilmes", "https://www.topfilmes.biz", [
        ("home", "{base}/", r'<div class="card">'),
        ("busca", "{base}/busca?q=resident", r'<div class="filme">'),
        ("detalhe", "{base}/assistir/resident-evil", r'/player/[^"\']+\?player=\d+'),
    ], r'(?:/direct/mf/[^"\']+|https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("AnimesCloud", "https://animesdrive.cloud", [
        ("home", "{base}/", r'<(?:article|div)[^>]*(?:card|item|movie|anime)'),
        ("busca", "{base}/?s=naruto", r'<(?:article|div)[^>]*(?:card|item|movie|anime)'),
    ], r'(?:https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("AnimesDigital", "https://animesdigital.net", [
        ("home", "{base}/", r'<(?:article|div|li)[^>]*(?:card|item|post)'),
        ("busca", "{base}/?s=naruto", r'<(?:article|div|li)[^>]*(?:card|item|post)'),
    ], r'(?:https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("DonghuaNoSekai", "https://donguanosekai.com", [
        ("home", "{base}/", r'<(?:article|div)[^>]*(?:card|item|post)'),
        ("busca", "{base}/?s=donghua", r'<(?:article|div)[^>]*(?:card|item|post)'),
    ], r'(?:https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("Doramas", "https://doramasonline.net", [
        ("home", "{base}/", r'<(?:article|div)[^>]*(?:card|item|post|filme)'),
        ("busca", "{base}/?s=amor", r'<(?:article|div)[^>]*(?:card|item|post|filme)'),
    ], r'(?:https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("MegaFlix", "https://megaflix.lol", [
        ("home", "{base}/", r'<(?:article|div)[^>]*(?:card|item|post|filme)'),
        ("busca", "{base}/?s=resident", r'<(?:article|div)[^>]*(?:card|item|post|filme)'),
    ], r'(?:https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("PobreFlix", "https://pobreflix.best", [
        ("home", "{base}/", r'class="gPoster"'),
        ("busca", "{base}/pesquisar/batman", r'class="gPoster"'),
    ], r'(?:fshd\.link/(?:filme|serie)/[\w/]+|https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
    ("UltraCine", "https://ultracine.autos", [
        ("home", "{base}/", r'<(?:article|div)[^>]*(?:card|item|post|filme)'),
        ("busca", "{base}/?s=resident", r'<(?:article|div)[^>]*(?:card|item|post|filme)'),
    ], r'(?:fshd\.link/(?:filme|serie)/[\w/]+|https?://[^\s"\'<>]+?\.(?:m3u8|mp4))'),
]

def main():
    report, results = [], {}
    ok_total = 0
    for name, base, probes, link_re in SITES:
        row = {"site": name, "base": base, "probes": {}, "links": []}
        for label, url_t, sel_re in probes:
            url = url_t.format(base=base)
            try:
                status, html = get(url)
                n = count(html, sel_re)
                row["probes"][label] = {"url": url, "status": status, "matches": n}
                if label.startswith("detalhe"):
                    row["links"] = sorted(set(re.findall(link_re, html)))[:5]
            except Exception as e:
                row["probes"][label] = {"url": url, "error": str(e)[:120]}
        results[name] = row
        good = sum(1 for p in row["probes"].values() if p.get("matches", 0) > 0)
        ok_total += good
        mark = "✅" if good == len(probes) else ("⚠️" if good else "❌")
        report.append(f"## {mark} {name} — {good}/{len(probes)} probes")
        for label, p in row["probes"].items():
            if "error" in p:
                report.append(f"- `{label}` ❌ {p['error']}")
            else:
                report.append(f"- `{label}` {'✅' if p['matches'] else '⚠️'} HTTP {p['status']} — {p['matches']} matches")
        if row["links"]:
            report.append(f"- 🔗 links extraídos: {', '.join('`%s`' % l for l in row['links'][:3])}")
        report.append("")

    summary = "\n".join(report)
    print(summary)
    with open("extract_report.json", "w") as f:
        json.dump(results, f, indent=1, ensure_ascii=False)
    import os
    if os.environ.get("GITHUB_STEP_SUMMARY"):
        with open(os.environ["GITHUB_STEP_SUMMARY"], "a") as f:
            f.write("# Extração de links e metadados — saimuelrepo\n\n" + summary)
    print(f"\nTOTAL: {ok_total} probes OK")
    # canário: exit 1 se TODOS os sites morreram (alerta real), senão exit 0
    return 1 if ok_total == 0 else 0

if __name__ == "__main__":
    sys.exit(main())
