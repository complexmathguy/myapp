/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.api;

import java.time.Instant
import java.util.*

import javax.persistence.*

import com.occulue.entity.*;


//-----------------------------------------------------------
// Event definitions
//-----------------------------------------------------------

// Company Events

data class CreatedCompanyEvent(
    @Id var companyId: UUID? = null,
    val name: String,
    val establishedOn: String,
    val revenue: Double,
        @AttributeOverrides(
      AttributeOverride( name = "street", column = Column(name = "address_street")),
      AttributeOverride( name = "city", column = Column(name = "address_city")),
      AttributeOverride( name = "state", column = Column(name = "address_state")),
      AttributeOverride( name = "zipCode", column = Column(name = "address_zipCode"))
    )
    val address: Address,
        @AttributeOverrides(
      AttributeOverride( name = "street", column = Column(name = "billingAddress_street")),
      AttributeOverride( name = "city", column = Column(name = "billingAddress_city")),
      AttributeOverride( name = "state", column = Column(name = "billingAddress_state")),
      AttributeOverride( name = "zipCode", column = Column(name = "billingAddress_zipCode"))
    )
    val billingAddress: Address,
    @Enumerated(EnumType.STRING) val type: CompanyType,
    @Enumerated(EnumType.STRING) val industry: Industry
)

data class RefreshedCompanyEvent(
    @Id var companyId: UUID? = null,
    val name: String,
    val establishedOn: String,
    val revenue: Double,
    val divisions:  Set<Division>,
    val boardMembers:  Set<Employee>,
        @AttributeOverrides(
      AttributeOverride( name = "street", column = Column(name = "address_street")),
      AttributeOverride( name = "city", column = Column(name = "address_city")),
      AttributeOverride( name = "state", column = Column(name = "address_state")),
      AttributeOverride( name = "zipCode", column = Column(name = "address_zipCode"))
    )
    val address: Address,
        @AttributeOverrides(
      AttributeOverride( name = "street", column = Column(name = "billingAddress_street")),
      AttributeOverride( name = "city", column = Column(name = "billingAddress_city")),
      AttributeOverride( name = "state", column = Column(name = "billingAddress_state")),
      AttributeOverride( name = "zipCode", column = Column(name = "billingAddress_zipCode"))
    )
    val billingAddress: Address,
    @Enumerated(EnumType.STRING) val type: CompanyType,
    @Enumerated(EnumType.STRING) val industry: Industry
)

data class ClosedCompanyEvent(@Id val companyId: UUID? = null)

// single association events

// multiple association events
data class AssignDivisionsToCompanyEvent(@Id val companyId: UUID, val addTo: Division )
data class RemoveDivisionsFromCompanyEvent(@Id val companyId: UUID, val removeFrom: Division )
data class AssignBoardMembersToCompanyEvent(@Id val companyId: UUID, val addTo: Employee )
data class DemomotedFromBoardMemberCommand(@Id val companyId: UUID, val removeFrom: Employee )


// Department Events

data class CreateDepartmentEvent(
    @Id var departmentId: UUID? = null,
    val name: String
)

data class UpdateDepartmentEvent(
    @Id var departmentId: UUID? = null,
    val name: String,
    val head: Employee,
    val employees:  Set<Employee>
)

data class DeleteDepartmentEvent(@Id val departmentId: UUID? = null)

// single association events
data class AssignHeadToDepartmentEvent(@Id val departmentId: UUID, val assignment: Employee )
data class UnAssignHeadFromDepartmentEvent(@Id val departmentId: UUID? = null )

// multiple association events
data class AssignEmployeesToDepartmentEvent(@Id val departmentId: UUID, val addTo: Employee )
data class RemoveEmployeesFromDepartmentEvent(@Id val departmentId: UUID, val removeFrom: Employee )


// Division Events

data class CreateDivisionEvent(
    @Id var divisionId: UUID? = null,
    val name: String
)

data class UpdateDivisionEvent(
    @Id var divisionId: UUID? = null,
    val name: String,
    val head: Employee,
    val departments:  Set<Department>
)

data class DeleteDivisionEvent(@Id val divisionId: UUID? = null)

// single association events
data class PromotedToDivisionHeadEvent(@Id val divisionId: UUID, val assignment: Employee )
data class DemomotedFromDivisionHeadCommand(@Id val divisionId: UUID? = null )

// multiple association events
data class AssignDepartmentsToDivisionEvent(@Id val divisionId: UUID, val addTo: Department )
data class RemoveDepartmentsFromDivisionEvent(@Id val divisionId: UUID, val removeFrom: Department )


// Employee Events

data class CreateEmployeeEvent(
    @Id var employeeId: UUID? = null,
    val firstName: String,
    val lastName: String,
    @Enumerated(EnumType.STRING) val type: EmploymentType
)

data class UpdateEmployeeEvent(
    @Id var employeeId: UUID? = null,
    val firstName: String,
    val lastName: String,
    @Enumerated(EnumType.STRING) val type: EmploymentType
)

data class DeleteEmployeeEvent(@Id val employeeId: UUID? = null)

// single association events

// multiple association events



