<%@ taglib prefix="dsp" uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_1" %>
<dsp:page>
	<dsp:droplet name="/com/droplet/MyFirstDroplet">
	  <dsp:param name="loop" value="4" />
	  
	  <dsp:oparam name="output">
	    hello!!! 123 <dsp:valueof param="value"/>
	  </dsp:oparam>
	</dsp:droplet>
</dsp:page>