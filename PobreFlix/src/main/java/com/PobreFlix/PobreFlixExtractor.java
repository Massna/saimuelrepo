package com.PobreFlix;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lagradost.cloudstream3.SubtitleFile;
import com.lagradost.cloudstream3.utils.ExtractorApiKt;
import com.lagradost.cloudstream3.utils.ExtractorLink;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/* JADX INFO: compiled from: PobreFlixExtractor.kt */
/* JADX INFO: loaded from: /tmp/cs3/PobreFlix/classes.dex */
/*
 * EXTRATOR REPARADO CONTRA O NOVO CONTRATO (IPS/Invision "videobox") em 2026-11-20.
 * Fluxo novo (decifrado do bundle plugins_plugins.js -> initPlayer):
 *   GET index.php?app=videobox&module=video&controller=view&do=playerData&id=<videoId>
 *   -> JSON {players:[{label,url,downloadUrl}], servers_dub, servers_leg, current_audio, ...}
 *   -> embed = players[key].url + id   (key = label.toLowerCase() sem nao-alfanumericos)
 *   -> loadExtractor(embed) por servidor (ordem: serverPriority; ids "hide" = trancado no
 *      mirror -> registro -> descartados honestamente).
 * Contrato antigo (C_Video('id','server') / e/getplay.php?id=&sv=): 100% morto — removido.
 */
public final class PobreFlixExtractor {

    @NotNull
    private static final String BASE_PLAYER = "https://www.pobreflixtv.codes/index.php?app=videobox&module=video&controller=view&do=playerData&id=";

    @NotNull
    private static final String TAG = "PobreFlix";

    @NotNull
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36";

    @NotNull
    private static final String REFERER = "https://www.pobreflixtv.codes/";

    /* Preservados por compatibilidade de estrutura (contrato antigo; não usados no fluxo novo). */
    @NotNull
    private static final Regex videoRegex = new Regex("C_Video\\('(\\d+)','(.*?)'\\)");

    @NotNull
    private static final Regex serverRegex = new Regex("'([^']*)'\\)");

    @NotNull
    private static final Map<String, Integer> serverPriority = MapsKt.mapOf(new Pair[]{TuplesKt.to("streamtape", 1), TuplesKt.to("filemoon", 2), TuplesKt.to("doodstream", 3), TuplesKt.to("mixdrop", 4)});

    @NotNull
    public static final PobreFlixExtractor INSTANCE = new PobreFlixExtractor();

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlixExtractor$getLinks$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlixExtractor.INSTANCE.getLinks(null, null, null, (Continuation) this);
        }
    }

    /* JADX INFO: renamed from: com.PobreFlix.PobreFlixExtractor$processItem$1, reason: invalid class name */
    static final class C00041 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C00041(Continuation<?> continuation) {
            super((Continuation) continuation);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PobreFlixExtractor.INSTANCE.processItem(null, null, null, null, (Continuation) this);
        }
    }

    private PobreFlixExtractor() {
    }

    @Nullable
    public final java.lang.Object getLinks(@org.jetbrains.annotations.NotNull java.lang.String data, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.SubtitleFile, kotlin.Unit> subtitleCallback, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super com.lagradost.cloudstream3.utils.ExtractorLink, kotlin.Unit> callback, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> continuation) {
        List<String> embeds;
        try {
            embeds = collectEmbeds(data);
        } catch (Throwable th) {
            continuation.resumeWith(ResultKt.createFailure(th));
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (embeds.isEmpty()) {
            return Boolean.TRUE;
        }
        processChain(embeds, 0, subtitleCallback, callback, continuation);
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    /* Cadeia de continuação: processa um embed por vez; loadExtractor é suspend, então o
     * avanço acontece ou síncrono (retorna valor) ou via resumeWith do step. */
    private final void processChain(final List<String> embeds, int start, final Function1<? super SubtitleFile, Unit> subtitleCallback, final Function1<? super ExtractorLink, Unit> callback, final Continuation<? super Boolean> continuation) {
        int i = start;
        while (i < embeds.size()) {
            final int next = i + 1;
            Continuation<Object> step = new Continuation<Object>() {
                @NotNull
                public CoroutineContext getContext() {
                    return continuation.getContext();
                }

                public void resumeWith(@NotNull Object result) {
                    /* Falha de um servidor não derruba os demais. */
                    PobreFlixExtractor.this.processChain(embeds, next, subtitleCallback, callback, continuation);
                }
            };
            Object r;
            try {
                r = ExtractorApiKt.loadExtractor(embeds.get(i), REFERER, subtitleCallback, callback, step);
            } catch (Throwable th) {
                r = ResultKt.createFailure(th);
            }
            if (r == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return;
            }
            i++;
        }
        continuation.resumeWith(Boolean.TRUE);
    }

    /* "movie|<id|url>" / "ep|<id|url>" -> lista ordenada de URLs de embed (url+id). */
    private final List<String> collectEmbeds(String data) throws Exception {
        List<String> out = new ArrayList<>();
        Pair<String, String> parsed = parseData(data);
        String payload = parsed.getSecond();
        String videoId = payload;
        if (payload == null || payload.isEmpty()) {
            return out;
        }
        if (!payload.matches("\\d+")) {
            Matcher m = Pattern.compile("-(\\d+)/?$").matcher(payload);
            if (m.find()) {
                videoId = m.group(1);
            } else {
                Document doc = Jsoup.connect(payload).userAgent(UA).timeout(20000).execute().parse();
                Element view = doc.selectFirst("#view[data-video-id]");
                if (view != null && !view.attr("data-video-id").isEmpty()) {
                    videoId = view.attr("data-video-id");
                }
            }
        }
        String json = Jsoup.connect(BASE_PLAYER + videoId).userAgent(UA).header("X-Requested-With", "XMLHttpRequest").header("Accept", "application/json").ignoreContentType(true).timeout(20000).execute().body();
        JsonObject root = new Gson().fromJson(json, JsonObject.class);
        if (root == null) {
            return out;
        }
        Map<String, String> prefixByKey = new LinkedHashMap<>();
        if (root.has("players") && root.get("players").isJsonArray()) {
            JsonArray players = root.getAsJsonArray("players");
            for (JsonElement el : players) {
                JsonObject p = el.getAsJsonObject();
                if (!p.has("label") || !p.has("url")) {
                    continue;
                }
                String key = p.get("label").getAsString().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
                prefixByKey.put(key, p.get("url").getAsString());
            }
        }
        /* servers_dub/servers_leg: query-string "key=id" (às vezes com &amp;). Dublado primeiro
         * (semântica: current_audio Dublado -> dub; ids "hide" = servidor trancado no mirror). */
        List<String[]> rows = new ArrayList<>(); // [embedUrl, key]
        collectServerMap(root, "servers_dub", prefixByKey, rows);
        collectServerMap(root, "servers_leg", prefixByKey, rows);
        Collections.sort(rows, new Comparator<String[]>() {
            public int compare(String[] a, String[] b) {
                return Integer.compare(extractPriority(a[1]), extractPriority(b[1]));
            }
        });
        for (String[] row : rows) {
            out.add(row[0]);
        }
        return out;
    }

    private final void collectServerMap(JsonObject root, String field, Map<String, String> prefixByKey, List<String[]> rows) {
        if (!root.has(field) || root.get(field).isJsonNull()) {
            return;
        }
        String raw = root.get(field).getAsString().replace("&amp;", "&");
        for (String pairStr : raw.split("&")) {
            int eq = pairStr.indexOf('=');
            if (eq <= 0) {
                continue;
            }
            String key = pairStr.substring(0, eq).toLowerCase(Locale.ROOT).trim();
            String id = pairStr.substring(eq + 1).trim();
            if (id.isEmpty() || "hide".equalsIgnoreCase(id)) {
                continue; /* trancado no mirror (registro) */
            }
            String prefix = prefixByKey.get(key);
            if (prefix == null || prefix.isEmpty()) {
                continue;
            }
            rows.add(new String[]{prefix + id, key});
        }
    }

    private final Pair<String, String> parseData(String data) {
        List<String> parts = StringsKt.split(data == null ? "" : data, new String[]{"|"}, false, 2);
        String str = (String) (parts.isEmpty() ? null : parts.get(0));
        if (str == null) {
            str = "";
        }
        String str2 = parts.size() > 1 ? parts.get(1) : data;
        if (str2 == null) {
            str2 = data;
        }
        return new Pair<>(str, str2);
    }

    /* Preservado do contrato antigo (sem efeito no fluxo novo). */
    private final String buildPlayerUrl(String type, String url) {
        StringBuilder sbAppend;
        String str;
        if (!kotlin.jvm.internal.Intrinsics.areEqual(type, "movie")) {
            return url;
        }
        if (StringsKt.contains(url, "?", false)) {
            sbAppend = new StringBuilder().append(url);
            str = "&area=online";
        } else {
            sbAppend = new StringBuilder().append(url);
            str = "/?area=online";
        }
        return sbAppend.append(str).toString();
    }

    /* Ordem preferencial dos servidores (semântica antiga preservada):
     * streamtape 1, filemoon 2, doodstream 3, mixdrop 4, demais por último. */
    /* JADX INFO: Access modifiers changed from: private */
    public final int extractPriority(String serverKey) {
        String lowerCase = serverKey != null ? serverKey.toLowerCase(Locale.ROOT) : "";
        if (lowerCase == null) {
            lowerCase = "";
        }
        Integer num = serverPriority.get(lowerCase);
        if (num != null) {
            return num.intValue();
        }
        return Integer.MAX_VALUE;
    }

    /* Um embed por chamada; a URL chega pronta (prefixo+id) do playerData. */
    /* JADX INFO: Access modifiers changed from: private */
    public final Object processItem(String embedUrl, String referer, Function1<? super SubtitleFile, Unit> subtitleCallback, Function1<? super ExtractorLink, Unit> callback, Continuation<? super Unit> continuation) {
        if (embedUrl == null || embedUrl.isEmpty()) {
            return Unit.INSTANCE;
        }
        final Continuation<? super Unit> cont = continuation;
        Continuation<Object> step = new Continuation<Object>() {
            @NotNull
            public CoroutineContext getContext() {
                return cont.getContext();
            }

            public void resumeWith(@NotNull Object result) {
                cont.resumeWith(Unit.INSTANCE);
            }
        };
        Object r;
        try {
            r = ExtractorApiKt.loadExtractor(embedUrl, referer != null ? referer : REFERER, subtitleCallback, callback, step);
        } catch (Throwable th) {
            r = ResultKt.createFailure(th);
        }
        if (r == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        return Unit.INSTANCE;
    }
}
