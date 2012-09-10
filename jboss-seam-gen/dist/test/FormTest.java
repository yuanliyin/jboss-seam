package @testPackage@;

import junit.framework.Assert;

import org.junit.Test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.mock.JUnitSeamTest;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;
import  @actionPackage@.@interfaceName@;

@RunWith(Arquillian.class)
public class @interfaceName@Test extends JUnitSeamTest {

   @Deployment(name="@interfaceName@Test")
   @OverProtocol("Servlet 3.0") 
   public static Archive<?> createDeployment()
   {
      // use in case jbpm is required in test deployment
      // return Deployments.jbpmSeamDeployment().addClasses(ProcessComponent.class);
      return Deployments.defaultWarDeployment()
            .addClasses(@interfaceName@.class);
   }
   
	@Test
	public void test_@methodName@() throws Exception {
		new FacesRequest("/@pageName@.xhtml") {
			@Override
			protected void updateModelValues() throws Exception {				
				//set form input to model attributes
				setValue("#{@componentName@.value}", "seam");
			}
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{@componentName@.@methodName@}");
			}
			@Override
			protected void renderResponse() {
				//check model attributes if needed
			   Assert.assertEquals("seam",  getValue("#{@componentName@.value}"));
			}
		}.run();
	}
}
