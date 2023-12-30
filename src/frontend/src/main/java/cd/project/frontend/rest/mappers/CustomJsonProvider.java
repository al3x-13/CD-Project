package cd.project.frontend.rest.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

public class CustomJsonProvider extends JacksonJsonProvider {
    public CustomJsonProvider() {
        super();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        setMapper(mapper);
    }
}
