<%@ taglib prefix="dsp" uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_1" %>
<dsp:importbean bean="/atg/userprofiling/ProfileFormHandler"/>
<dsp:importbean bean="/atg/userprofiling/ProfileErrorMessageForEach"/>

<dsp:page>
	<dsp:droplet name="ProfileErrorMessageForEach">
      <dsp:param name="exceptions" bean="ProfileFormHandler.formExceptions"/>
      <dsp:oparam name="output">
     	<dsp:valueof param="message"/> <br/>
      </dsp:oparam>
    </dsp:droplet>

	<dsp:form method="post">	
		<dsp:input bean="ProfileFormHandler.value.member" type="hidden" value="true"/>		
		<table>
			<tr>
				<td>First Name</td><td><dsp:input bean="ProfileFormHandler.value.firstName"></dsp:input></td>
			</tr>
			<tr>
				<td>Middle Name</td><td><dsp:input bean="ProfileFormHandler.value.middleName"></dsp:input></td>
			</tr>
			<tr>
				<td>Last Name</td><td><dsp:input bean="ProfileFormHandler.value.lastName"></dsp:input></td>
			</tr>
			<tr>
				<td>Email</td><td><dsp:input bean="ProfileFormHandler.value.email"></dsp:input></td>
			</tr>	
			<tr>
				<td>Login Name</td><td><dsp:input bean="ProfileFormHandler.value.login"></dsp:input></td>
			</tr>		
			<tr>
				<td>Password</td><td><dsp:input type="password" bean="ProfileFormHandler.value.password"></dsp:input></td>
			</tr>
			<tr>
				<td>Gender</td>
				<td>
					<dsp:input bean="ProfileFormHandler.value.gender" type="radio" value="male"/>Male
    				<dsp:input bean="ProfileFormHandler.value.gender" type="radio" value="female"/>Female
    			</td>
			</tr>
			<tr>
				<td></td><td><dsp:input type="submit" value="Register" bean="ProfileFormHandler.create"></dsp:input></td>
			</tr>
		</table>
		<dsp:input type="hidden" bean="ProfileFormHandler.createSuccessURL" value="welcome.jsp"></dsp:input>
		<dsp:input type="hidden" bean="ProfileFormHandler.createErrorUrl" value="registration.jsp"></dsp:input>
	</dsp:form>	
</dsp:page>