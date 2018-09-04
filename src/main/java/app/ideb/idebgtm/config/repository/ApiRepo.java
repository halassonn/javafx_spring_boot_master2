package app.ideb.idebgtm.config.repository;

public interface ApiRepo {
    String POST(String url, Object payload);
    String GET(String url);
    String DELETE(String url, String id);
    String PUT(String url, Object payload);
    String PATCH(String url, Object payload);
}