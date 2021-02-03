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
 * Division business delegate class.
 * <p>
 * This class implements the Business Delegate design pattern for the purpose of:
 * <ol>
 * <li>Reducing coupling between the business tier and a client of the business tier by hiding all business-tier implementation details</li>
 * <li>Improving the available of Division related services in the case of a Division business related service failing.</li>
 * <li>Exposes a simpler, uniform Division interface to the business tier, making it easy for clients to consume a simple Java object.</li>
 * <li>Hides the communication protocol that may be required to fulfill Division business related services.</li>
 * </ol>
 * <p>
 * @author 
 */
public class DivisionBusinessDelegate 
extends BaseBusinessDelegate
{
//************************************************************************
// Public Methods
//************************************************************************
    /** 
     * Default Constructor 
     */
    public DivisionBusinessDelegate() 
	{
	}

        
   /**
	* Division Business Delegate Factory Method
	*
	* Returns a singleton instance of DivisionBusinessDelegate().
	* All methods are expected to be self-sufficient.
	*
	* @return 	DivisionBusinessDelegate
	*/
	public static DivisionBusinessDelegate getDivisionInstance()
	{
	    if ( singleton == null )
	    {
	    	singleton = new DivisionBusinessDelegate();
	    }
	    
	    return( singleton );
	}
 
    /**
     * Method to retrieve the Division via an DivisionPrimaryKey.
     * @param 	key
     * @return 	Division
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    public Division getDivision( DivisionPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "DivisionBusinessDelegate:getDivision - ";
        if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        Division returnBO = null;
                
        DivisionDAO dao = getDivisionDAO();
        
        try
        {
            returnBO = dao.findDivision( key );
        }
        catch( Exception exc )
        {
            String errMsg = "DivisionBusinessDelegate:getDivision( DivisionPrimaryKey key ) - unable to locate Division with key " + key.toString() + " - " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDivisionDAO( dao );
        }        
        
        return returnBO;
    }


    /**
     * Method to retrieve a collection of all Divisions
     *
     * @return 	ArrayList<Division> 
     * @exception ProcessingException Thrown if any problems
     */
    public ArrayList<Division> getAllDivision() 
    throws ProcessingException
    {
    	String msgPrefix				= "DivisionBusinessDelegate:getAllDivision() - ";
        ArrayList<Division> array	= null;
        
        DivisionDAO dao = getDivisionDAO();
    
        try
        {
            array = dao.findAllDivision();
        }
        catch( Exception exc )
        {
            String errMsg = msgPrefix + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDivisionDAO( dao );
        }        
        
        return array;
    }

   /**
    * Creates the provided BO.
    * @param		businessObject 	Division
    * @return       Division
    * @exception    ProcessingException
    * @exception	IllegalArgumentException
    */
	public Division createDivision( Division businessObject )
    throws ProcessingException, IllegalArgumentException
    {
		String msgPrefix = "DivisionBusinessDelegate:createDivision - ";
		
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        // return value once persisted
        DivisionDAO dao  = getDivisionDAO();
        
        try
        {
            businessObject = dao.createDivision( businessObject );
        }
        catch (Exception exc)
        {
            String errMsg = "DivisionBusinessDelegate:createDivision() - Unable to create Division" + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDivisionDAO( dao );
        }        
        
        return( businessObject );
        
    }

   /**
    * Saves the underlying BO.
    * @param		businessObject		Division
    * @return       what was just saved
    * @exception    ProcessingException
    * @exception  	IllegalArgumentException
    */
    public Division saveDivision( Division businessObject ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "DivisionBusinessDelegate:saveDivision - ";
    	
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
                
        // --------------------------------
        // If the businessObject has a key, find it and apply the businessObject
        // --------------------------------
        DivisionPrimaryKey key = businessObject.getDivisionPrimaryKey();
                    
        if ( key != null )
        {
            DivisionDAO dao = getDivisionDAO();

            try
            {                    
                businessObject = (Division)dao.saveDivision( businessObject );
            }
            catch (Exception exc)
            {
                String errMsg = "DivisionBusinessDelegate:saveDivision() - Unable to save Division" + exc;
                LOGGER.warning( errMsg );
                throw new ProcessingException( errMsg  );
            }
            finally
            {
                releaseDivisionDAO( dao );
            }
            
        }
        else
        {
            String errMsg = "DivisionBusinessDelegate:saveDivision() - Unable to create Division due to it having a null DivisionPrimaryKey."; 
            
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
		        
        return( businessObject );
        
    }
   
   /**
    * Deletes the associatied value object using the provided primary key.
    * @param		key 	DivisionPrimaryKey    
    * @exception 	ProcessingException
    */
    public void delete( DivisionPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {    	
    	String msgPrefix = "DivisionBusinessDelegate:saveDivision - ";
    	
		if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        DivisionDAO dao  = getDivisionDAO();

        try
        {                    
            dao.deleteDivision( key );
        }
        catch (Exception exc)
        {
            String errMsg = msgPrefix + "Unable to delete Division using key = "  + key + ". " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseDivisionDAO( dao );
        }
        		
        return;
    }
        

// business methods
    /**
     * Returns the Division specific DAO.
     *
     * @return      Division DAO
     */
    public DivisionDAO getDivisionDAO()
    {
        return( new com.occulue.dao.DivisionDAO() ); 
    }

    /**
     * Release the DivisionDAO back to the FrameworkDAOFactory
     */
    public void releaseDivisionDAO( com.occulue.dao.DivisionDAO dao )
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
    protected static DivisionBusinessDelegate singleton = null;
    private static final Logger LOGGER = Logger.getLogger(Division.class.getName());
    
}



