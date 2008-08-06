<%@ page contentType="text/html;charset=UTF-8" language="java" import="org.apache.empire.struts2.websample.db.*"%>
<%@ taglib prefix="e" uri="/empire-tags" %>
<jsp:useBean id="db" scope="application" type="org.apache.empire.struts2.websample.db.SampleDB"/>
<jsp:useBean id="action" scope="request" type="org.apache.empire.struts2.websample.web.actions.EmployeeDetailAction"/>
<% 
	SampleDB.Employees EMP = db.T_EMPLOYEES;
%>
<html>
<head>
    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <title><e:text value="!application.title"/></title>
</head>
<body>
<div class="titleDiv"><e:text value="!application.title"/></div>
<h1>
<e:if test="<%= action.getEmployee().isNew() %>">
	<e:text value="!page.label.add"/>
</e:if>
<e:else>
	<e:text value="!page.label.update"/>
</e:else>
</h1>
<e:actionerrors />
<e:actionmessage />

<e:form record="<%= action.getEmployee() %>" action="!doSave">

	<!-- 
	<s:textfield name="employee.firstName" value="%{employee.firstName}" label="%{getText('label.firstName')}" size="40"/>
	<s:textfield name="employee.lastName" value="%{employee.lastName}" label="%{getText('label.lastName')}" size="40"/>
	<s:textfield name="employee.age" value="%{employee.age}" label="%{getText('label.age')}" size="20"/>
	-->

	<e:control column="<%= EMP.C_EMPLOYEE_ID %>" />
	<e:control column="<%= EMP.C_SALUTATION %>" />
    <e:control column="<%= EMP.C_FIRSTNAME 	%>" />
    <e:control column="<%= EMP.C_LASTNAME 	%>" />
    <e:control column="<%= EMP.C_GENDER 	%>" />
    <e:control column="<%= EMP.C_DATE_OF_BIRTH 	%>" />
    <e:control column="<%= EMP.C_PHONE_NUMBER %>" />
    <e:control column="<%= EMP.C_DEPARTMENT_ID%>" />
    <e:control column="<%= EMP.C_RETIRED %>" />
	<e:control column="<%= EMP.C_UPDATE_TIMESTAMP %>" />

	<%-- 
    <s:submit value="%{getText('button.label.submit')}"/>
    <s:submit value="%{getText('button.label.cancel')}" name="redirect-action:employeeDetail!doCancel"/>
    --%>
	
	<tr style="padding-top:20px">
	  <td>&nbsp;</td>
      <td><e:submit text="!button.label.save"   cssStyle="width:100px" embed="false"/>
          <e:submit text="!button.label.cancel" cssStyle="width:100px" action="!doCancel" embed="false"/>
          <e:submit text="!button.label.delete" cssStyle="width:100px" action="!doDelete" embed="false"/>
      </td>
    </tr>
     
</e:form>
</body>
</html>