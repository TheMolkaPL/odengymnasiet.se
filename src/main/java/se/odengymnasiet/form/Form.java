package se.odengymnasiet.form;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.SortedMap;

public class Form {

    private final Request request;
    private final Response response;

    private final SortedMap<Namespace, List<Attribute>> attributes;

    public Form(Request request, Response response,
                SortedMap<Namespace, List<Attribute>> attributes) {
        this.request = request;
        this.response = response;

        this.attributes = attributes;
    }

    public Request getRequest() {
        return this.request;
    }

    public Response getResponse() {
        return this.response;
    }

    public SortedMap<Namespace, List<Attribute>> getAttributes() {
        return this.attributes;
    }

    public static FormBuilder builder() {
        return new FormBuilder();
    }

    public void validate() {
        Request request = this.getRequest();

        this.attributes.forEach((namespace, attributes) -> {
            QueryParamsMap params = request.queryMap(namespace.getNamespace());
            attributes.forEach(attribute -> {
                QueryParamsMap value = params.get(attribute.getAttribute());
                if (value.value() == null && !attribute.isRequired()) {
                    // TODO
                }
            });
        });
    }
}
