<%@ taglib prefix="dsp" uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_1" %>
<dsp:importbean bean="/atg/userprofiling/ProfileFormHandler"/>
<dsp:importbean bean="/atg/dynamo/droplet/ErrorMessageForEach"/>

<dsp:page>
	<dsp:droplet name="ErrorMessageForEach">
		<dsp:param name="exceptions" bean="ProfileFormHandler.formExceptions"/>
		<dsp:oparam name="output">
			<b>
				<font color="red">
					<dsp:valueof param="message"/>
				</font>
			</b>
		</dsp:oparam>
	</dsp:droplet>

	<dsp:form method="post">
		<table>
			<tr>
				<td>Username</td><td><dsp:input bean="ProfileFormHandler.value.login"></dsp:input></td>
			</tr>
			<tr>
				<td>Password</td><td><dsp:input type="password" bean="ProfileFormHandler.value.password"></dsp:input></td>
			</tr>
			<tr>
				<td></td><td><dsp:input type="submit" value="Login" bean="ProfileFormHandler.submit"></dsp:input></td>
			</tr>
		</table>
		<dsp:input type="hidden" bean="ProfileFormHandler.loginSuccessUrl" value="welcome.jsp"></dsp:input>
		<dsp:input type="hidden" bean="ProfileFormHandler.loginErrorUrl" value="login.jsp"></dsp:input>
	</dsp:form>	
</dsp:page>