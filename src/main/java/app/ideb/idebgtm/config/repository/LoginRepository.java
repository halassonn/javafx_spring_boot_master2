package app.ideb.idebgtm.config.repository;

import org.json.simple.parser.ParseException;

public interface LoginRepository {
    String login(String endpoint, Object payload) throws ParseException;
}
