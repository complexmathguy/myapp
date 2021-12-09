/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.api

import org.axonframework.modelling.command.TargetAggregateIdentifier

import java.util.*

import javax.persistence.*

import com.occulue.entity.*;

//-----------------------------------------------------------
// Command definitions
//-----------------------------------------------------------

// Company Commands
data class CreateCompanyCommand(
    @TargetAggregateIdentifier var companyId: UUID? = null,
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

data class RefreshCompanyCommand(
    @TargetAggregateIdentifier var companyId: UUID? = null,
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

data class CloseCompanyCommand(@TargetAggregateIdentifier var companyId: UUID? = null)

// single association commands

// multiple association commands
data class AssignDivisionsToCompanyCommand(@TargetAggregateIdentifier val companyId: UUID, val addTo: Division )
data class RemoveDivisionsFromCompanyCommand(@TargetAggregateIdentifier val companyId: UUID, val removeFrom: Division )
data class AssignBoardMembersToCompanyCommand(@TargetAggregateIdentifier val companyId: UUID, val addTo: Employee )
data class DemoteFromBoardMemberCommand(@TargetAggregateIdentifier val companyId: UUID, val removeFrom: Employee )


// Department Commands
data class CreateDepartmentCommand(
    @TargetAggregateIdentifier var departmentId: UUID? = null,
    val name: String
)

data class UpdateDepartmentCommand(
    @TargetAggregateIdentifier var departmentId: UUID? = null,
    val name: String,
    val head: Employee,
    val employees:  Set<Employee>
)

data class DeleteDepartmentCommand(@TargetAggregateIdentifier var departmentId: UUID? = null)

// single association commands
data class AssignHeadToDepartmentCommand(@TargetAggregateIdentifier val departmentId: UUID, val assignment: Employee )
data class UnAssignHeadFromDepartmentCommand(@TargetAggregateIdentifier val departmentId: UUID )

// multiple association commands
data class AssignEmployeesToDepartmentCommand(@TargetAggregateIdentifier val departmentId: UUID, val addTo: Employee )
data class RemoveEmployeesFromDepartmentCommand(@TargetAggregateIdentifier val departmentId: UUID, val removeFrom: Employee )


// Division Commands
data class CreateDivisionCommand(
    @TargetAggregateIdentifier var divisionId: UUID? = null,
    val name: String
)

data class UpdateDivisionCommand(
    @TargetAggregateIdentifier var divisionId: UUID? = null,
    val name: String,
    val head: Employee,
    val departments:  Set<Department>
)

data class DeleteDivisionCommand(@TargetAggregateIdentifier var divisionId: UUID? = null)

// single association commands
data class PromoteToDivisionHeadCommand(@TargetAggregateIdentifier val divisionId: UUID, val assignment: Employee )
data class DemoteFromDivisionHeadCommand(@TargetAggregateIdentifier val divisionId: UUID )

// multiple association commands
data class AssignDepartmentsToDivisionCommand(@TargetAggregateIdentifier val divisionId: UUID, val addTo: Department )
data class RemoveDepartmentsFromDivisionCommand(@TargetAggregateIdentifier val divisionId: UUID, val removeFrom: Department )


// Employee Commands
data class CreateEmployeeCommand(
    @TargetAggregateIdentifier var employeeId: UUID? = null,
    val firstName: String,
    val lastName: String,
    @Enumerated(EnumType.STRING) val type: EmploymentType
)

data class UpdateEmployeeCommand(
    @TargetAggregateIdentifier var employeeId: UUID? = null,
    val firstName: String,
    val lastName: String,
    @Enumerated(EnumType.STRING) val type: EmploymentType
)

data class DeleteEmployeeCommand(@TargetAggregateIdentifier var employeeId: UUID? = null)

// single association commands

// multiple association commands



