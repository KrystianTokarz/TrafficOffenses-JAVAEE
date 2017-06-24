package custom.component;

import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.FacesListener;
import java.io.IOException;
import java.util.Map;

@FacesComponent(value = "custom.component.MessageComponent")
public class MessageComponent extends UIInput {


    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        String clientId = getClientId(context);
        char sep = UINamingContainer.getSeparatorChar(context);
        encodeInputField(context, clientId + sep + "inputfield");
    }

    private void encodeInputField(FacesContext context,String clientId) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        Boolean required = (boolean) getAttributes().get("required");
        if(required){
            writer.writeText("*",null);
        }
        writer.startElement("input", this);
        writer.writeAttribute("type", "text", null);
        writer.writeAttribute("name", clientId, "clientId");
        writer.endElement("input");
    }

    @Override
    public void decode(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        String clientId = getClientId(context);
        char sep = UINamingContainer.getSeparatorChar(context);
        String submitted_hello_msg = ((String) requestMap.get(clientId + sep + "inputfield"));
        setSubmittedValue(submitted_hello_msg);
    }
}
