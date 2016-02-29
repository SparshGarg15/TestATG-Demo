<%@ taglib prefix="dsp" uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_1" %>
<dsp:importbean bean="/atg/userprofiling/Profile"/>

<dsp:page>
Hello <dsp:valueof bean="Profile.firstName"/>, Welcome to ATG!!!

<br/><br/>
Displaying Products GO:

<dsp:droplet name="/atg/commerce/catalog/ProductLookup">
  <dsp:param value="100" name="id"/>  
  <dsp:oparam name="output">
    <dsp:droplet name="/atg/dynamo/droplet/ForEach">
	     <dsp:param param="element.childSKUs" name="array"/>
	     <dsp:oparam name="outputStart">
		     <p><b>Child Skus:</b>
		     <ul>
	   		</dsp:oparam>
	     <dsp:oparam name="output">
		     <li>
		     <dsp:valueof param="element.displayName"/>
		     </li>			     
	    </dsp:oparam>
	    <dsp:oparam name="outputEnd">
	    	</ul>
	    </dsp:oparam>
	 </dsp:droplet>
  </dsp:oparam>
</dsp:droplet>

<dsp:droplet name="/atg/commerce/catalog/CategoryLookup">
	<dsp:param value="2" name="id"/>
	<dsp:oparam name="output">
	     <dsp:droplet name="/atg/dynamo/droplet/ForEach">
		     <dsp:param param="element.childCategories" name="array"/>
		     <dsp:oparam name="outputStart">
			     <p><b>Child Categories:</b>
			     <ul>
     		</dsp:oparam>
		     <dsp:oparam name="output">
			     <li>
			     <dsp:valueof param="element.displayName"/>			     
			        <dsp:droplet name="/atg/dynamo/droplet/ForEach">
					     <dsp:param param="element.childProducts" name="array"/>
					     <dsp:oparam name="outputStart">
						     <p><b>Child Products:</b>
						     <ul>
					     </dsp:oparam>
					     <dsp:oparam name="output">		     
					     	<li>
						     <dsp:valueof param="element.description"/>
						     	<dsp:droplet name="/atg/dynamo/droplet/ForEach">
							     <dsp:param param="element.childSKUs" name="array"/>
							     <dsp:oparam name="outputStart">
								     <p><b>Child Skus:</b>
								     <ul>
							     </dsp:oparam>
							     <dsp:oparam name="output">
							     	<li>
							     		<dsp:valueof param="element.id"/>
							     		<dsp:valueof param="element.displayName"/>
							     		<dsp:valueof param="element.listPrice"/>
							     		
							     		<dsp:droplet name="/atg/commerce/inventory/InventoryLookup"> </td>
										   <dsp:param param="element.id" name="itemId"/>
										   <dsp:oparam name="output">
										     This item is
										     <dsp:valueof param="inventoryInfo.availabilityStatusMsg"/><br>
										     There are
										     <dsp:valueof param="inventoryInfo.stockLevel"/>
										     left in the inventory.<br>
										   </dsp:oparam>
										</dsp:droplet>
							     		
							     	</li>
								</dsp:oparam>
								<dsp:oparam name="outputEnd">
							     	</ul>
							     </dsp:oparam>
								</dsp:droplet>
						    </li>		     
					     </dsp:oparam>
					     <dsp:oparam name="outputEnd">
					     	</ul>
					     </dsp:oparam>
				    </dsp:droplet>
			     </li>			     
		    </dsp:oparam>
		    <dsp:oparam name="outputEnd">
		    	</ul>
		    </dsp:oparam>
   		</dsp:droplet>   		
 </dsp:oparam>
</dsp:droplet>

</dsp:page> 