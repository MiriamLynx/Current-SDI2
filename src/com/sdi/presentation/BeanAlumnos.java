package com.sdi.presentation;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.business.AlumnosService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Alumno;

@ManagedBean(name="controller")
@SessionScoped
public class BeanAlumnos implements Serializable{
	      private static final long serialVersionUID = 55555L;
		  // Se añade este atributo de entidad para recibir el alumno concreto selecionado de la tabla o de un formulario
	      // Es necesario inicializarlo para que al entrar desde el formulario de AltaForm.xml se puedan
	      // dejar los avalores en un objeto existente.
	    //uso de inyección de dependencia
	      @ManagedProperty(value="#{alumno}") 
	      private BeanAlumno alumno;
	      
	      private Alumno[] alumnos = null;
			
	      public BeanAlumno getAlumno() { return alumno; }
	      public void setAlumno(BeanAlumno alumno) {this.alumno = alumno;}
		
          		  
		  public Alumno[] getAlumnos () {
			    return(alumnos);
			  }	   
	       public void setAlumnos(Alumno[] alumnos) {
				  this.alumnos = alumnos;
		     }  
	       
	     //Se inicia correctamente el MBean inyectado si JSF lo hubiera crea
	     //y en caso contrario se crea. (hay que tener en cuenta que es un Bean de sesión)
	     //Se usa @PostConstruct, ya que en el contructor no se sabe todavía si el Managed Bean
	     //ya estaba construido y en @PostConstruct SI.
	     @PostConstruct
	     public void init() {    	  
	       System.out.println("BeanAlumnos - PostConstruct"); 
	       //Buscamos el alumno en la sesión. Esto es un patrón factoría claramente.
	       alumno = (BeanAlumno) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(new String("alumno"));
	       //si no existe lo creamos e inicializamos
	       if (alumno == null) { 
	         System.out.println("BeanAlumnos - No existia");
	         alumno = new BeanAlumno();
	         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( "alumno", alumno);
	       }
	     }
	     @PreDestroy
	     public void end()  {
	         System.out.println("BeanAlumnos - PreDestroy");
	     }

	       
	       public void iniciaAlumno(ActionEvent event) {
	    	 alumno.iniciaAlumno(event);	
	    	  }
	       
		public String listado() {
			AlumnosService service;
			try {
				service = Factories.services.createAlumnosService();
				alumnos = (Alumno[]) service.getAlumnos().toArray(new Alumno[0]);
	
				return "exito"; // Nos vamos a la vista listado.xhtml
	
			} catch (Exception e) {
				e.printStackTrace();
				return "error"; // Nos vamos la vista de error
			}
		}
		
		public String baja(Alumno alumno) {
		       AlumnosService service;
				  try {
				  // Acceso a la implementacion de la capa de negocio 
					// a trav��s de la factor��a
					service = Factories.services.createAlumnosService();
			      //Aliminamos el alumno seleccionado en la tabla
					service.deleteAlumno(alumno.getId());
				  //Actualizamos el javabean de alumnos inyectado en la tabla.
					alumnos = (Alumno [])service.getAlumnos().toArray(new Alumno[0]);
					return "exito";   //Nos vamos a la vista de listado.
					
				  } catch (Exception e) {
					e.printStackTrace();  
					return "error";     //Nos vamos a la vista de error
				  }
				  
		 	  }
	       public String edit() {
		       AlumnosService service;
				  try {
				  // Acceso a la implementacion de la capa de negocio 
					// a trav��s de la factor��a
					service = Factories.services.createAlumnosService();
			      //Recargamos el alumno seleccionado en la tabla de la base de datos por si hubiera cambios.
					alumno = (BeanAlumno)service.findById(alumno.getId());
					return "exito";     //Nos vamos a la vista de Edición.
					
				  } catch (Exception e) {
					e.printStackTrace();  
					return "error";        //Nos vamos a la vista de error.
				  }
				  
		 	  }
	       
	       public String salva() {
		       AlumnosService service;
				  try {
				  // Acceso a la implementacion de la capa de negocio 
					// a trav��s de la factor��a
					service = Factories.services.createAlumnosService();
			      //Salvamos o actualizamos el alumno segun sea una operacion de alta o de edici��n
					if (alumno.getId() == null) {
						service.saveAlumno(alumno);  
					}
					else {
						service.updateAlumno(alumno); 
					} 
					//Actualizamos el javabean de alumnos inyectado en la tabla
					alumnos = (Alumno [])service.getAlumnos().toArray(new Alumno[0]);
					return "exito";  //Nos vamos a la vista de listado.
					
				  } catch (Exception e) {
					  e.printStackTrace();
					return "error";    //Nos vamos a la vista de error.
				  }
				  
		 	  }
	}


	
