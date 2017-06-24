package infrastructure.api;

import domainmodel.domain.event.Event;
import domainmodel.embaddable.UserData;

import javax.ejb.Local;

@Local
public interface PdfManager {

    byte[] createPdf(Event event, long numberOfPoints, long amount );
}
