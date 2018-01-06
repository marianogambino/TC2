package ar.edu.unimoron.appum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ar.edu.unimoron.asyncTask.AsyncTaskAlumnosEnLaUM;
import ar.edu.unimoron.asyncTask.AsyncTaskCursos;
import ar.edu.unimoron.asyncTask.AsyncTaskExamenes;
import ar.edu.unimoron.listaNovedad.NovedadItem;
import ar.edu.unimoron.menu.MenuUmItem;
import ar.edu.unimoron.menu.MenuUmListAdapter;
import ar.edu.unimoron.response.LoginResponse;
import ar.edu.unimoron.services.servicioUM.NotifyListenerService;
import ar.edu.unimoron.services.servicioUM.NotifyService;
import ar.edu.unimoron.umDB.UnimoronDB;

public class WelcomeActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private List<MenuUmItem> menuItems;
    private MenuUmListAdapter adapter;
	
    private LoginResponse loginResponse;
	private String nombreAlumno;
	
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView( R.layout.activity_welcome );
		
		System.setProperty("http.keepAlive", "false");
		
		Intent intent = this.getIntent();
		String datosLogin = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		try{			
			this.loginResponse = new ObjectMapper().readValue( datosLogin , LoginResponse.class);
			UnimoronDB db = new UnimoronDB(this);
			db.open();
			//abro base de datos y grabo el login response
			if (db.getLogin() ==null){//(this.loginResponse == null) {
				ObjectMapper mapper = new ObjectMapper();
				
				String strUsr = mapper.writeValueAsString(this.loginResponse.getUsuario().getUsername());
				String strPass = mapper.writeValueAsString(this.loginResponse.getUsuario().getPassword());
				//this.loginResponse.getUsuario().getPassword();
				
				String strResponse =mapper.writeValueAsString(this.loginResponse); 
				
			//if (db.getLogin() ==null)
				//db.saveLogin(this.loginResponse.toString());
				db.saveLogin(strUsr, strPass, strResponse);
			}
			db.close();
			
			this.nombreAlumno = loginResponse.getUsuario().getAlumno().getNombre();
			mTitle = String.format("%s %s", getResources().getString( R.string.bienvenidosTitle ) ,  nombreAlumno); //getTitle();
			getActionBar().setTitle( mTitle );
			mDrawerTitle = getResources().getString( R.string.menu );
	        // load slide menu items
	        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
	
	        // nav drawer icons from resources
	        navMenuIcons = getResources()
	                .obtainTypedArray(R.array.menu_drawer_icons);
	
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.menu_drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.menu_list_view);
	
	        menuItems = new ArrayList<MenuUmItem>();
	
	        // agregar un nuevo item al menu deslizante
	        // Seccion - perfil
	        menuItems.add(new MenuUmItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1) ));
	        // Seccion - cursos
	        menuItems.add(new MenuUmItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1) ));
	        // Seccion - examenes
	        menuItems.add(new MenuUmItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1) ));
	        // Seccion - novedades UM
	        menuItems.add(new MenuUmItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));        
	        //Seccion - Alumnos en la UM
	        menuItems.add(new MenuUmItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));        
	       //Seccion6 - Mi ubicacion
	        menuItems.add(new MenuUmItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));             
	      
	        // Recycle the typed array
	        navMenuIcons.recycle();
	
	        mDrawerList.setOnItemClickListener( new SlideMenuClickListener() );
	
	        // setting the nav drawer list adapter
	        adapter = new MenuUmListAdapter( getApplicationContext(),  menuItems );
	        mDrawerList.setAdapter( adapter );
	
	        // enabling action bar app icon and behaving it as toggle button
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	
	        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	                R.drawable.ic_drawer, //nav menu toggle icon
	                R.string.app_name, // nav drawer open - description for accessibility
	                R.string.app_name // nav drawer close - description for accessibility
	        ) {
	            public void onDrawerClosed(View view) {
	                //getActionBar().setTitle(mTitle);
	                // calling onPrepareOptionsMenu() to show action bar icons
	               // invalidateOptionsMenu();
	            }
	
	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle(mDrawerTitle);
	                // calling onPrepareOptionsMenu() to hide action bar icons
	                invalidateOptionsMenu();
	            }
	        };
	        
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	
	        if (savedInstanceState == null) {
	            // on first time display view for first nav item
	            displayView(-1);
	        }
        
        //se inician los servicios
	    	     
         startService( new Intent( this , NotifyService.class)   );
         
         startService( new Intent( this , NotifyListenerService.class).putExtra("matricula", loginResponse.getUsuario().getAlumno().getMatricula() )   );
      
	        
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	
	
	public void displayView(int position) {
        // update the main content by replacing fragments
        android.app.Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new PerfilFragment();
                break;
            case 1:
            	this.consultarCursos();
                break;
            case 2:
            	this.consultarExamenes();
                break;
            case 3:
            	this.consultarNovedades();
                break;
            case 4:
            	this.consultarAlumnosEnLaUM();
                break;
            case 5:
                fragment = new MiUbicacionUmFragment();
                break;    
            default:
            	 fragment = new WelcomeFragment();
                break;
        }

        if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            //setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("Mariano", "WelcomeActivity - Error cuando se creo el fragment");
        }
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.action_settings) {
			return true;
		}*/
		
		if (id == R.id.cerrarSesion) {
			cerrarSesion();
		}		
		//return super.onOptionsItemSelected(item);
		
		if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    
	}

	 /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
	
	//Que el botón de desplegar siempre este sincronizado
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    //Igual con la configuración
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }   

    
    public boolean onKeyDown(int keycode, KeyEvent event) {
	    if (keycode == KeyEvent.KEYCODE_BACK) {
	    	this.getActionBar().setTitle( String.format("%s: %s", getResources().getString( R.string.bienvenidosTitle ) ,  nombreAlumno) );
	    	displayView(-1); 
	    }
	    return false; // super.onKeyDown(keycode, event);
	}
    
    private void cerrarSesion( ){
        
        stopService( new Intent( this , NotifyService.class)   );        
        stopService( new Intent( this , NotifyListenerService.class)   );
        
    	UnimoronDB db = new UnimoronDB(this);
		db.open();
		db.logout();
		db.close();
		
    	//this.onBackPressed();
		Intent intent = new Intent( this , LoginActivity.class);    	
		this.startActivity(intent);
    }

    
    private void consultarCursos(){    	        
        LoginResponse loginResponse = this.getLoginResponse();   
        String matricula = loginResponse.getUsuario().getAlumno().getMatricula();
        String[] param = new String[1];	
	    param[0] = matricula;        
	    AsyncTaskCursos consultaCursos = new AsyncTaskCursos( this );
	    consultaCursos.execute(param);    	
    }
    
    private void consultarExamenes(){    	
        LoginResponse loginResponse = this.getLoginResponse();   
        String matricula = loginResponse.getUsuario().getAlumno().getMatricula();
        String[] param = new String[1];	
	    param[0] = matricula;        
	    AsyncTaskExamenes consultaExamenes= new AsyncTaskExamenes(this );
	    consultaExamenes.execute(param);
    }
    
    private void consultarNovedades(){
    	
	    UnimoronDB db = new UnimoronDB(this);
		db.open();
		
		String novedades = null;
		//List<NovedadItem> listaNove = new ArrayList<NovedadItem>();
		List<String> listaNove = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		listaNove = db.getNovedades();
		db.close();
		
		try {
			novedades = mapper.writeValueAsString(listaNove);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        Intent intent = new Intent( this , NovedadesActivity.class);
	    intent.putExtra( MainActivity.EXTRA_MESSAGE , novedades );
		this.startActivity(intent);
		
		
	}
    
    
    private void consultarAlumnosEnLaUM(){
    	LoginResponse loginResponse = this.getLoginResponse();   
        String matricula = loginResponse.getUsuario().getAlumno().getMatricula();
        Long idCarrera = loginResponse.getUsuario().getAlumno().getCarrera().getId();
        String[] param = new String[2];	
  	    param[0] = matricula;
  	    param[1] = idCarrera.toString();
        AsyncTaskAlumnosEnLaUM consultaAlumnos= new AsyncTaskAlumnosEnLaUM( this );
        consultaAlumnos.execute(param);
    }


    

	/**
	 * @return the loginResponse
	 */
	public LoginResponse getLoginResponse() {
		return loginResponse;
	}


	/**
	 * @param loginResponse the loginResponse to set
	 */
	public void setLoginResponse(LoginResponse loginResponse) {
		this.loginResponse = loginResponse;
	}
    
    
    
}
