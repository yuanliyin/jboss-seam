package org.jboss.seam.core;

import static org.jboss.seam.InterceptionType.NEVER;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.Intercept;
import org.jboss.seam.annotations.Unwrap;

/**
 * Manager component for an EJB 3.0 entity instance. Allows
 * auto-fetching of contextual entities. The identifier
 * is determined by evaluating an EL expression and then
 * using JSF type conversion if necessary.
 * 
 * @author Gavin King
 *
 */
@Intercept(NEVER)
public class ManagedEntity
{
   private EntityManager entityManager;
   private Object id;
   private String entityClass;
   private String idClass;
   private Object newInstance;
   private String idConverterId;
   private Converter idConverter;
   
   public EntityManager getEntityManager()
   {
      return entityManager;
   }

   public void setEntityManager(EntityManager entityManager)
   {
      this.entityManager = entityManager;
   }

   public Object getId()
   {
      return id;
   }

   public void setId(Object id)
   {
      this.id = id;
   }
   
   public String getEntityClass()
   {
      return entityClass;
   }

   public void setEntityClass(String entityClass)
   {
      this.entityClass = entityClass;
   }

   @Unwrap
   public Object getInstance() throws Exception
   {
      Class<?> clazz = Class.forName(entityClass);
      if (id==null)
      {
         if (newInstance==null)
         {
            newInstance = clazz.newInstance();
         }
         return newInstance;
      }
      else
      {
         return entityManager.find( clazz, getConvertedId() );
      }
   }
   
   //////////// TODO: copy/paste from ManagedHibernateEntity ///////////////////
   
   private Object getConvertedId() throws Exception
   {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (idConverter==null)
      {
         if (idConverterId==null)
         {
            //TODO: guess the id class using @Id
            idConverter = facesContext.getApplication().createConverter( Class.forName(idClass) );
         }
         else
         {
            idConverter = facesContext.getApplication().createConverter(idConverterId); //cache the lookup
         }
      }
      
      if (idConverter==null)
      {
         return id;
      }
      else
      {
         return idConverter.getAsObject( 
               facesContext, 
               facesContext.getViewRoot(), 
               (String) id 
            );
      }
   }

   public String getIdConverterId()
   {
      return idConverterId;
   }

   public void setIdConverterId(String converterId)
   {
      this.idConverterId = converterId;
   }

   public Converter getIdConverter()
   {
      return idConverter;
   }

   public void setIdConverter(Converter converter)
   {
      this.idConverter = converter;
   }

   public String getIdClass()
   {
      return idClass;
   }

   public void setIdClass(String idClass)
   {
      this.idClass = idClass;
   }

}
