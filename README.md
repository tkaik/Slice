# About Slice

## Purpose

Slice is a framework that glues Sling and Google Guice together. It eases mapping between
resources to classed and to using dependency injection inside Sling and CQ applications.

## Prerequisites

* CQ / Apache Sling 2
* Maven 2.x, 3.x

## Installation

Checkout the source code:

    cd [folder of your choice]
    git clone git://github.com/Cognifide/Slice.git
    cd Slice

Compile and install:

    mvn clean package install

## Usage

Add dependencied to your POM file:

    (...)
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-core-api</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-core</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-mapper</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-mapper-api</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-cq</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-cq-taglib</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-validation-api</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>com.cognifide.slice</groupId>
        <artifactId>slice-validation</artifactId>
        <version>3.0.0</version>
        <scope>compile</scope>
    </dependency>
    (...)

Prepare Injector of your application in Activator. Example activator:

	import java.util.ArrayList;
	import java.util.List;
	
	import org.osgi.framework.BundleActivator;
	import org.osgi.framework.BundleContext;
	
	import com.cognifide.slice.api.context.ContextScope;
	import com.cognifide.slice.api.injector.InjectorBuilder;
	import com.cognifide.slice.commons.SliceModulesFactory;
	import com.cognifide.slice.core.internal.context.SliceContextScope;
	import com.cognifide.slice.cq.module.CQModulesFactory;
	import com.cognifide.slice.validation.ValidationModulesFactory;
	import com.google.inject.Module;

	public class Activator implements BundleActivator {
	
		private static final String BUNDLE_NAME_FILTER = "com\\.company\\.application\\.webapp\\..*";
	
		private static final String BASE_PACKAGE = "com.company.application";
	
		private static final String INJECTOR_NAME = "injectorName";
	
		@Override
		public void start(BundleContext bundleContext) throws Exception {
			final ContextScope scope = new SliceContextScope();
			final InjectorBuilder injectorBuilder = new InjectorBuilder(bundleContext, INJECTOR_NAME, scope);
			
			List<Module> sliceModules = SliceModulesFactory.createModules(bundleContext, INJECTOR_NAME,
					BUNDLE_NAME_FILTER, BASE_PACKAGE);
			List<Module> cqModules = CQModulesFactory.createModules();
			List<Module> validationModules = ValidationModulesFactory.createModules();
			List<Module> customModules = createCustomModules();
			
			injectorBuilder.installModules(sliceModules);
			injectorBuilder.installModules(cqModules);
			injectorBuilder.installModules(validationModules);
			injectorBuilder.installModules(customModules);
			
			injectorBuilder.start();
		}
	
		private List<Module> createCustomModules() {
			List<Module> applicationModules = new ArrayList<Module>();
			//populate list with your modules
			return applicationModules;
		}
	}

SliceModulesFactory requires some String parameters. It will look for any classes in filters matching BUNDLE_NAME_FILTER under package BASE_PACKAGE for classes
annotated with @SliceResource and bind them to provider that automatically maps resource properties to fields.
You can install many Slice Resource modules on one Injector.

## Mapping Slice Resources

Map any class with @SliceResource module to get auto mapping of resource properties to class fields:

    @SliceResource
    public class RichTextModel {

        @JcrProperty
        @Unescaped
        private String text;

		@Inject
        public RichTextModel() {
        }

        public String getText() {
            return text;
        }

	}

Map current resource in .jsp file to this class:

    <slice:lookup appName="injectorName" var="model" type="<%=RichTextModel.class%>"/>

Use properties from model variable:

    <div class="richText">
        ${model.text}
    </div>

# Commercial Support

Technical support can be made available if needed. Please [contact us](https://www.cognifide.com/get-in-touch/) for more details.

We can:

* prioritize your feature request,
* tailor the product to your needs,
* provide a training for your engineers,
* support your development teams.

More documentation
------------------
* [Slice Wiki](https://github.com/Cognifide/Slice/wiki)
* [Cognifide.com](http://cognifide.com)
* [Maven](http://maven.apache.org)
* [CRX](http://www.day.com/day/en/products/crx.html)
* [CRX API](http://dev.day.com/content/docs/en/crx/current/how_to/package_manager.html#Package%20Manager%20HTTP%20Service%20API)