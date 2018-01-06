/**
 * 
 */
package ar.edu.unimoron.context.alumnoExamen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import ar.edu.fwk.model.Dominio;
import edu.fwk.context.ContextAware;
import edu.fwk.interfaces.IContext;

/**
 * @author mariano
 *
 */
public class AlumnoExamenCtx extends ContextAware implements IContext {
	
	private static String CONTEXT_ALUMNO_EXAMEN = "ALUMNOEXAMEN";
	
	
	private static AlumnoExamenCtx instance = new AlumnoExamenCtx();
	
	public static AlumnoExamenCtx getInstance(){
		return instance;
	}
	
	@Override
	public Map<String, Map<String, Object>> resolveContext(String domainName,
			Map<String, Map<String, Object>> anotherContexts, Context contextAndroid) {
		
		Dominio[] dom = preparaAlumnoExamen( domainName ); 		
		return super.resolveContext(dom, contextAndroid, CONTEXT_ALUMNO_EXAMEN, anotherContexts);	
		
	}
	
	private Dominio[] preparaAlumnoExamen(String domainName){ 		
		Map<String, Map<String , Object> > dataCtx = new HashMap<String, Map<String,Object>>();
        Map<String,Object> contextValue = new HashMap<String, Object>();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        contextValue.put("fecha0", format.format( new Date()) );
        return super.createDomain(dataCtx, domainName, CONTEXT_ALUMNO_EXAMEN);         
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

}
