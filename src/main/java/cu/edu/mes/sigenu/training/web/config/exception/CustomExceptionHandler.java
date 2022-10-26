package cu.edu.mes.sigenu.training.web.config.exception;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.view.facelets.TagAttributeException;

import org.springframework.beans.factory.BeanCreationException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;


public class CustomExceptionHandler extends ExceptionHandlerWrapper {
	private static final Logger log = Logger.getLogger(CustomExceptionHandler.class.getCanonicalName());
	private ExceptionHandler wrapped;

	CustomExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {

		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// get the exception from context
			Throwable t = context.getException();

			final FacesContext fc = FacesContext.getCurrentInstance();
			final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
			final NavigationHandler nav = fc.getApplication().getNavigationHandler();
			final ConfigurableNavigationHandler nav2 = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();               

			try {
				if(CurrentUserUtils.isLogged()){ //usuario autenticado

					if(t instanceof ELException || t instanceof ViewExpiredException || t instanceof MismatchedInputException || t instanceof BeanCreationException || t instanceof TagAttributeException){
						nav.handleNavigation(fc, null, "/pages/security/login?faces-redirect=true");
						//nav2.performNavigation("/pages/security/login.js");                 
					}

				} else { //usuario no autenticado
					nav.handleNavigation(fc, null, "/pages/security/login?faces-redirect=true");
				}

				//redirect error page
				fc.renderResponse();
			} catch (Exception e) {
				nav.handleNavigation(fc, null, "/pages/security/login?faces-redirect=true");
			} finally {
				//remove it from queue
				i.remove();
			}
		}
		//parent hanle
		getWrapped().handle();
	}

	private String getCause(Throwable t){
		String cause = "Desconocida";
		
		if(t.getCause() != null && t.getCause().getCause() != null && t.getCause().getCause().getCause() != null)
			cause = t.getCause().getCause().getCause().toString();
		
		return cause;
	}
	
	private String getErrorLocation(Throwable t){
		String errorLocation = "Desconocida";

		if(t.getCause() != null && t.getCause().getCause() != null && t.getCause().getCause().getCause() != null){
			StackTraceElement[] traceElements = t.getCause().getCause().getCause().getStackTrace();

			if(traceElements.length > 0){
				errorLocation = traceElements[0].toString();

				if(!errorLocation.contains("mx.cusoft.erpxml")){
					boolean stop = false;
					int pos = 1;

					while(!stop && pos < traceElements.length){
						String traceInfo = traceElements[pos].toString();

						if(traceInfo.contains("mx.cusoft.erpxml")){
							stop = true;
							errorLocation += " <- " + traceInfo;
						}

						pos++;
					}

					if(!stop)//entonces poner la ultima linea de la traza
						errorLocation += " <- " + traceElements[traceElements.length - 1];    				
				}
			}
		}    		    			

		return errorLocation;
	}
}
