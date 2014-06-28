package bs.index;

public class Item {
    
    private String url;
    private String title;
    private String content;
    
    public Item() {
    }
    
    public Item(String url, String title, String content) {
        this.url = url;
        this.title = title;
        this.content = content;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append("[id=").append(id).append(",title=").append(title).append(",content=").append(content).append("]");
        sb.append("url = ").append(url).append("\ntitle = ").append(title) ;
        return sb.toString();
    }
}