/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.delegate;

import java.util.*;
import java.util.HashMap;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

    import com.occulue.primarykey.*;
    import com.occulue.dao.*;
    import com.occulue.bo.*;
    
import com.occulue.exception.*;

/**
 * Department business delegate class.
 * <p>
 * This class implements the Business Delegate design pattern for the purpose of:
 * <ol>
 * <li>Reducing coupling between the business tier and a client of the business tier by hiding all business-tier implementation details</li>
 * <li>Improving the available of Department related services in the case of a Department business related service failing.</li>
 * <li>Exposes a simpler, uniform Department interface to the business tier, making it easy for clients to consume a simple Java object.</li>
 * <li>Hides the communication protocol that may be required to fulfill Department business related services.</li>
 * </ol>
 * <p>
 * @author 
 */
public class DepartmentBusinessDelegate 
extends BaseBusinessDelegate
{
//************************************************************************
// Public Methods
//************************************************************************
    /** 
     * Default Constructor 
     */
    public DepartmentBusinessDelegate() 
	{
	}

        
   /**
	* Department Business Delegate Factory Method
	*
	* Returns a singleton instance of DepartmentBusinessDelegate().
	* All methods are expected to be self-sufficient.
	*
	* @return 	DepartmentBusinessDelegate
	*/
	public static DepartmentBusinessDelegate getDepartmentInstance()
	{
	    if ( singleton == null )
	    {
	    	singleton = new DepartmentBusinessDelegate();
	    }
	    
	    return( singleton );
	}
 
    /**
     * Method to retrieve the Department via an DepartmentPrimaryKey.
     * @param 	key
     * @return 	Department
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    public Department getDepartment( DepartmentPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "DepartmentBusinessDelegate:getDepartment - ";
        if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        Department returnBO = null;
                
        DepartmentDAO dao = getDepartmentDAO();
        
        try
        {
            returnBO = dao.findDepartment( key );
        }
        catch( Exception exc )
        {
            String errMsg = "DepartmentBusinessDelegate:getDepartment( DepartmentPrimaryKey key ) - unable to locate Department with key " + key.toString() + " - " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDepartmentDAO( dao );
        }        
        
        return returnBO;
    }


    /**
     * Method to retrieve a collection of all Departments
     *
     * @return 	ArrayList<Department> 
     * @exception ProcessingException Thrown if any problems
     */
    public ArrayList<Department> getAllDepartment() 
    throws ProcessingException
    {
    	String msgPrefix				= "DepartmentBusinessDelegate:getAllDepartment() - ";
        ArrayList<Department> array	= null;
        
        DepartmentDAO dao = getDepartmentDAO();
    
        try
        {
            array = dao.findAllDepartment();
        }
        catch( Exception exc )
        {
            String errMsg = msgPrefix + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDepartmentDAO( dao );
        }        
        
        return array;
    }

   /**
    * Creates the provided BO.
    * @param		businessObject 	Department
    * @return       Department
    * @exception    ProcessingException
    * @exception	IllegalArgumentException
    */
	public Department createDepartment( Department businessObject )
    throws ProcessingException, IllegalArgumentException
    {
		String msgPrefix = "DepartmentBusinessDelegate:createDepartment - ";
		
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        // return value once persisted
        DepartmentDAO dao  = getDepartmentDAO();
        
        try
        {
            businessObject = dao.createDepartment( businessObject );
        }
        catch (Exception exc)
        {
            String errMsg = "DepartmentBusinessDelegate:createDepartment() - Unable to create Department" + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDepartmentDAO( dao );
        }        
        
        return( businessObject );
        
    }

   /**
    * Saves the underlying BO.
    * @param		businessObject		Department
    * @return       what was just saved
    * @exception    ProcessingException
    * @exception  	IllegalArgumentException
    */
    public Department saveDepartment( Department businessObject ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "DepartmentBusinessDelegate:saveDepartment - ";
    	
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
                
        // --------------------------------
        // If the businessObject has a key, find it and apply the businessObject
        // --------------------------------
        DepartmentPrimaryKey key = businessObject.getDepartmentPrimaryKey();
                    
        if ( key != null )
        {
            DepartmentDAO dao = getDepartmentDAO();

            try
            {                    
                businessObject = (Department)dao.saveDepartment( businessObject );
            }
            catch (Exception exc)
            {
                String errMsg = "DepartmentBusinessDelegate:saveDepartment() - Unable to save Department" + exc;
                LOGGER.warning( errMsg );
                throw new ProcessingException( errMsg  );
            }
            finally
            {
                releaseDepartmentDAO( dao );
            }
            
        }
        else
        {
            String errMsg = "DepartmentBusinessDelegate:saveDepartment() - Unable to create Department due to it having a null DepartmentPrimaryKey."; 
            
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
		        
        return( businessObject );
        
    }
   
   /**
    * Deletes the associatied value object using the provided primary key.
    * @param		key 	DepartmentPrimaryKey    
    * @exception 	ProcessingException
    */
    public void delete( DepartmentPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {    	
    	String msgPrefix = "DepartmentBusinessDelegate:saveDepartment - ";
    	
		if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        DepartmentDAO dao  = getDepartmentDAO();

        try
        {                    
            dao.deleteDepartment( key );
        }
        catch (Exception exc)
        {
            String errMsg = msgPrefix + "Unable to delete Department using key = "  + key + ". " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDepartmentDAO( dao );
        }
        		
        return;
    }
        

// business methods
    /**
     * Returns the Department specific DAO.
     *
     * @return      Department DAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return( new com.occulue.dao.DepartmentDAO() ); 
    }

    /**
     * Release the DepartmentDAO back to the FrameworkDAOFactory
     */
    public void releaseDepartmentDAO( com.occulue.dao.DepartmentDAO dao )
    {
        dao = null;
    }

// AIB : #getBusinessMethodImplementations( $classObject.getName() $classObject $classObject.getBusinessMethods() $classObject.getInterfaces() )
// ~AIB
     
//************************************************************************
// Attributes
//************************************************************************

   /**
    * Singleton instance
    */
    protected static DepartmentBusinessDelegate singleton = null;
    private static final Logger LOGGER = Logger.getLogger(Department.class.getName());
    
}



