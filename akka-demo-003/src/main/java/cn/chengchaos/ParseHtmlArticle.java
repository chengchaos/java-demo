package cn.chengchaos;

public class ParseHtmlArticle {

    private String url;

    private String body;

    public ParseHtmlArticle(String url, String body) {
        this.url = url;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }
}
