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
 * Company business delegate class.
 * <p>
 * This class implements the Business Delegate design pattern for the purpose of:
 * <ol>
 * <li>Reducing coupling between the business tier and a client of the business tier by hiding all business-tier implementation details</li>
 * <li>Improving the available of Company related services in the case of a Company business related service failing.</li>
 * <li>Exposes a simpler, uniform Company interface to the business tier, making it easy for clients to consume a simple Java object.</li>
 * <li>Hides the communication protocol that may be required to fulfill Company business related services.</li>
 * </ol>
 * <p>
 * @author 
 */
public class CompanyBusinessDelegate 
extends BaseBusinessDelegate
{
//************************************************************************
// Public Methods
//************************************************************************
    /** 
     * Default Constructor 
     */
    public CompanyBusinessDelegate() 
	{
	}

        
   /**
	* Company Business Delegate Factory Method
	*
	* Returns a singleton instance of CompanyBusinessDelegate().
	* All methods are expected to be self-sufficient.
	*
	* @return 	CompanyBusinessDelegate
	*/
	public static CompanyBusinessDelegate getCompanyInstance()
	{
	    if ( singleton == null )
	    {
	    	singleton = new CompanyBusinessDelegate();
	    }
	    
	    return( singleton );
	}
 
    /**
     * Method to retrieve the Company via an CompanyPrimaryKey.
     * @param 	key
     * @return 	Company
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    public Company getCompany( CompanyPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "CompanyBusinessDelegate:getCompany - ";
        if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        Company returnBO = null;
                
        CompanyDAO dao = getCompanyDAO();
        
        try
        {
            returnBO = dao.findCompany( key );
        }
        catch( Exception exc )
        {
            String errMsg = "CompanyBusinessDelegate:getCompany( CompanyPrimaryKey key ) - unable to locate Company with key " + key.toString() + " - " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseCompanyDAO( dao );
        }        
        
        return returnBO;
    }


    /**
     * Method to retrieve a collection of all Companys
     *
     * @return 	ArrayList<Company> 
     * @exception ProcessingException Thrown if any problems
     */
    public ArrayList<Company> getAllCompany() 
    throws ProcessingException
    {
    	String msgPrefix				= "CompanyBusinessDelegate:getAllCompany() - ";
        ArrayList<Company> array	= null;
        
        CompanyDAO dao = getCompanyDAO();
    
        try
        {
            array = dao.findAllCompany();
        }
        catch( Exception exc )
        {
            String errMsg = msgPrefix + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseCompanyDAO( dao );
        }        
        
        return array;
    }

   /**
    * Creates the provided BO.
    * @param		businessObject 	Company
    * @return       Company
    * @exception    ProcessingException
    * @exception	IllegalArgumentException
    */
	public Company createCompany( Company businessObject )
    throws ProcessingException, IllegalArgumentException
    {
		String msgPrefix = "CompanyBusinessDelegate:createCompany - ";
		
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        // return value once persisted
        CompanyDAO dao  = getCompanyDAO();
        
        try
        {
            businessObject = dao.createCompany( businessObject );
        }
        catch (Exception exc)
        {
            String errMsg = "CompanyBusinessDelegate:createCompany() - Unable to create Company" + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseCompanyDAO( dao );
        }        
        
        return( businessObject );
        
    }

   /**
    * Saves the underlying BO.
    * @param		businessObject		Company
    * @return       what was just saved
    * @exception    ProcessingException
    * @exception  	IllegalArgumentException
    */
    public Company saveCompany( Company businessObject ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "CompanyBusinessDelegate:saveCompany - ";
    	
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
                
        // --------------------------------
        // If the businessObject has a key, find it and apply the businessObject
        // --------------------------------
        CompanyPrimaryKey key = businessObject.getCompanyPrimaryKey();
                    
        if ( key != null )
        {
            CompanyDAO dao = getCompanyDAO();

            try
            {                    
                businessObject = (Company)dao.saveCompany( businessObject );
            }
            catch (Exception exc)
            {
                String errMsg = "CompanyBusinessDelegate:saveCompany() - Unable to save Company" + exc;
                LOGGER.warning( errMsg );
                throw new ProcessingException( errMsg  );
            }
            finally
            {
                releaseCompanyDAO( dao );
            }
            
        }
        else
        {
            String errMsg = "CompanyBusinessDelegate:saveCompany() - Unable to create Company due to it having a null CompanyPrimaryKey."; 
            
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
		        
        return( businessObject );
        
    }
   
   /**
    * Deletes the associatied value object using the provided primary key.
    * @param		key 	CompanyPrimaryKey    
    * @exception 	ProcessingException
    */
    public void delete( CompanyPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {    	
    	String msgPrefix = "CompanyBusinessDelegate:saveCompany - ";
    	
		if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        CompanyDAO dao  = getCompanyDAO();

        try
        {                    
            dao.deleteCompany( key );
        }
        catch (Exception exc)
        {
            String errMsg = msgPrefix + "Unable to delete Company using key = "  + key + ". " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseCompanyDAO( dao );
        }
        		
        return;
    }
        

// business methods
    /**
     * Returns the Company specific DAO.
     *
     * @return      Company DAO
     */
    public CompanyDAO getCompanyDAO()
    {
        return( new com.occulue.dao.CompanyDAO() ); 
    }

    /**
     * Release the CompanyDAO back to the FrameworkDAOFactory
     */
    public void releaseCompanyDAO( com.occulue.dao.CompanyDAO dao )
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
    protected static CompanyBusinessDelegate singleton = null;
    private static final Logger LOGGER = Logger.getLogger(Company.class.getName());
    
}



