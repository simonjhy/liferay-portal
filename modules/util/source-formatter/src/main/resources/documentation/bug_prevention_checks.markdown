# Bug Prevention Checks

Check | File Extensions | Description
----- | --------------- | -----------
AnonymousClassCheck | .java | Checks for serialization issue when using anonymous class. |
ArquillianCheck | .java | Checks for correct use of `com.liferay.arquillian.extension.junit.bridge.junit.Arquillian`. |
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | .java | Checks that there are no import statements that use the * notation. |
BNDBundleActivatorCheck | .bnd | Validates property value for `Bundle-Activator`. |
BNDBundleCheck | .bnd | Validates `Liferay-Releng-*` properties. |
BNDBundleInformationCheck | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName`. |
BNDDefinitionKeysCheck | .bnd | Validates definition keys in `.bnd` files. |
BNDDirectoryNameCheck | .bnd | Checks if the directory names of the submodules match the parent module name. |
BNDExportsCheck | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages. |
BNDIncludeResourceCheck | .bnd | Checks for unnesecarry including of `test-classes/integration`. |
BNDLiferayEnterpriseAppCheck | .bnd | Checks for correct use of property `Liferay-Enterprise-App`. |
BNDLiferayRelengBundleCheck | .bnd | Checks if `.lfrbuild-release-src` file exists for DXP module with `Liferay-Releng-Bundle: true` |
BNDLiferayRelengCategoryCheck | .bnd | Validates `Liferay-Releng-Category` properties |
BNDMultipleAppBNDsCheck | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module). |
BNDRangeCheck | .bnd | Checks for use or range expressions. |
BNDSchemaVersionCheck | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion`. |
BNDWebContextPathCheck | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory. |
CDNCheck | | Checks the URL in `artifact.properties` files. |
CQLKeywordCheck | .cql | Checks that Cassandra keywords are upper case. |
CodeownersFileLocationCheck | CODEOWNERS | Checks that `CODEOWNERS` files are located in `.github` directory. |
CompanyIterationCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `CompanyLocalService.forEachCompany` or `CompanyLocalService.forEachCompanyId` is used when iterating over companies |
CompatClassImportsCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that classes are imported from `compat` modules, when possible. |
ConsumerTypeAnnotationCheck | .java | Performs several checks on classes with @ConsumerType annotation. |
DTOEnumCreationCheck | .java | Checks the creation of DTO enum. |
DeprecatedAPICheck | .java | Finds calls to deprecated classes, constructors, fields or methods. |
EmptyConstructorCheck | .java | Finds unnecessary empty constructors. |
FactoryCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where `*Factory` should be used when creating new instances of an object. |
FilterStringWhitespaceCheck | .java | Finds missing and unnecessary whitespace in the value of the filter string in `ServiceTrackerFactory.open` or `WaiterUtil.waitForFilter`. |
GenericTypeCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that generics are always specified to provide compile-time checking and removing the risk of `ClassCastException` during runtime. |
GradleDependencyArtifactsCheck | .gradle | Checks that value `default` is not used for attribute `version`. |
GradleDependencyConfigurationCheck | .gradle | Validates the scope of dependencies in build gradle files. |
GradleDependencyVersionCheck | .gradle | Checks the version for dependencies in gradle build files. |
GradleExportedPackageDependenciesCheck | .gradle | Validates dependencies in gradle build files. |
GradleJavaVersionCheck | .gradle | Checks values of properties `sourceCompatibility` and `targetCompatibility` in gradle build files. |
GradlePropertiesCheck | .gradle | Validates property values in gradle build files. |
GradleProvidedDependenciesCheck | .gradle | Validates the scope of dependencies in build gradle files. |
GradleRequiredDependenciesCheck | .gradle | Validates the dependencies in `/required-dependencies/required-dependencies/build.gradle`. |
GradleTestDependencyVersionCheck | .gradle | Checks the version for dependencies in gradle build files. |
IncorrectFileLocationCheck | | Checks that `/src/*/java/` only contains `.java` files. |
JSLodashDependencyCheck | .js or .jsx | Finds incorrect use of `AUI._`. |
JSONDeprecatedPackagesCheck | .ipynb, .json or .npmbridgerc | Finds incorrect use of deprecated packages in `package.json` files. |
JSONPackageJSONBNDVersionCheck | .ipynb, .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
JSONPackageJSONCheck | .ipynb, .json or .npmbridgerc | Checks content of `package.json` files. |
JSONPackageJSONDependencyVersionCheck | .ipynb, .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
JSONValidationCheck | .ipynb, .json or .npmbridgerc | Validates content of `.json` files. |
JSPArrowFunctionCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no array functions. |
JSPIllegalSyntaxCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of `System.out.print`, `console.log` or `debugger.*` in `.jsp` files. |
JSPIncludeCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates values of `include` in `.jsp` files. |
JSPLanguageKeysCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds missing language keys in `Language.properties`. |
JSPLanguageUtilCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where Locale is passed to `LanguageUtil.get` instead of `HttpServletRequest`. |
JSPLogFileNameCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates the value that is passed to `LogFactoryUtil.getLog` in `.jsp`. |
JSPMethodCallsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that type `LiferayPortletResponse` is used to call `getNamespace()`. |
JSPMissingTaglibsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for missing taglibs. |
JSPSendRedirectCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no calls to `HttpServletResponse.sendRedirect` from `jsp` files. |
JSPSessionKeysCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
JSPTagAttributesCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Performs several checks on tag attributes. |
JavaAPISignatureCheck | .java | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures. |
JavaAbstractMethodCheck | .java | Finds incorrect `abstract` methods in `interface`. |
JavaAnnotationsCheck | .java | Performs several checks on annotations. |
JavaAnonymousInnerClassCheck | .java | Performs several checks on anonymous classes. |
JavaBaseUpgradeCallableCheck | .java | Checks that BaseUpgradeCallable is used instead of Callable or Runnable in Upgrade and Verify classes. |
JavaBooleanStatementCheck | .java | Performs several checks on variable declaration of type `Boolean`. |
JavaBooleanUsageCheck | .java | Finds incorrect use of passing boolean values in `setAttribute` calls. |
JavaCleanUpMethodSuperCleanUpCheck | .java | Checks that `cleanUp` method in `*Tag` class with `@Override` annotation calls the `cleanUp` method of the superclass. |
JavaCleanUpMethodVariablesCheck | .java | Checks that variables in `Tag` classes get cleaned up properly. |
JavaCollatorUtilCheck | .java | Checks for correct use of `Collator`. |
JavaComponentAnnotationsCheck | .java | Performs several checks on classes with `@Component` annotation. |
JavaConfigurationAdminCheck | .java | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration`. |
JavaConfigurationCategoryCheck | .java | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web`. |
JavaDeprecatedKernelClassesCheck | .java | Finds calls to deprecated classes `com.liferay.portal.kernel.util.CharPool` and `com.liferay.portal.kernel.util.StringPool`. |
JavaFinderCacheCheck | .java | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath`. |
JavaFinderImplCustomSQLCheck | .java | Checks that hardcoded SQL values in `*FinderImpl` classes match the SQL in the `.xml` file in the `custom-sql` directory. |
JavaIgnoreAnnotationCheck | .java | Finds methods with `@Ignore` annotation in test classes. |
JavaIllegalImportsCheck | .java | Finds cases of incorrect use of certain classes. |
JavaIndexableCheck | .java | Checks that the type gets returned when using annotation `@Indexable`. |
JavaInterfaceCheck | .java | Checks that `interface` is not `static`. |
JavaInternalPackageCheck | .java | Performs several checks on class in `internal` package. |
JavaJSPDynamicIncludeCheck | .java | Performs several checks on `*JSPDynamicInclude` class. |
JavaLocalSensitiveComparisonCheck | .java | Checks that `java.text.Collator` is used when comparing localized values. |
JavaLogClassNameCheck | .java | Checks the name of the class that is passed in `LogFactoryUtil.getLog`. |
JavaLogLevelCheck | .java | Checks that the correct log messages are printed. |
JavaMapBuilderGenericsCheck | .java | Finds missing or unnecessary generics on `*MapBuilder.put` calls. |
JavaMetaAnnotationsCheck | .java | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta`. |
JavaMissingOverrideCheck | .java | Finds missing @Override annotations. |
JavaMissingXMLPublicIdsCheck | .java | Finds missing public IDs for check XML files. |
JavaModifiedServiceMethodCheck | .java | Finds missing empty lines before `removedService` or `addingService` calls. |
JavaModuleComponentCheck | .java | Checks for use of `@Component` in `-api` or `-spi` modules. |
JavaModuleExposureCheck | .java | Checks for exposure of `SPI` types in `API`. |
JavaModuleIllegalImportsCheck | .java | Finds cases of incorrect use of certain classes in modules. |
JavaModuleInternalImportsCheck | .java | Finds cases where a module imports an `internal` class from another class. |
JavaModuleJavaxPortletInitParamTemplatePathCheck | .java | Validates the value of `javax.portlet.init-param.template-path`. |
JavaModuleServiceProxyFactoryCheck | .java | Finds cases of `ServiceProxyFactory.newServiceTrackedInstance`. |
JavaModuleServiceReferenceCheck | .java | Finds cases where `@BeanReference` annotation should be used instead of `@ServiceReference` annotation. |
JavaModuleTestCheck | .java | Checks package names in tests. |
JavaOSGiReferenceCheck | .java | Performs several checks on classes with `@Component` annotation. |
JavaPackagePathCheck | .java | Checks that the package name matches the file location. |
JavaProcessCallableCheck | .java | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID`. |
JavaProviderTypeAnnotationCheck | .java | Performs several checks on classes with `@ProviderType` annotation. |
JavaRedundantConstructorCheck | .java | Finds unnecessary empty constructor. |
JavaReleaseInfoCheck | .java | Validates information in `ReleaseInfo.java`. |
JavaResultSetCheck | .java | Checks for correct use `java.sql.ResultSet.getInt(int)`. |
JavaSeeAnnotationCheck | .java | Checks for nested annotations inside `@see`. |
JavaServiceImplCheck | .java | Ensures that `afterPropertiesSet` and `destroy` methods in `*ServiceImpl` always call the method with the same name in the superclass. |
JavaServiceUtilCheck | .java | Checks that there are no calls to `*ServiceImpl` from a `*ServiceUtil` class. |
JavaStagedModelDataHandlerCheck | .java | Finds missing method `setMvccVersion` in class extending `BaseStagedModelDataHandler` in module that has `mvcc-enabled=true` in `service.xml`. |
JavaStaticBlockCheck | .java | Performs several checks on `static` blocks. |
JavaStaticMethodCheck | .java | Finds cases where methods are unncessarily declared static. |
JavaStaticVariableDependencyCheck | .java | Checks that static variables in the same class that depend on each other are correctly defined. |
JavaStopWatchCheck | .java | Checks for potential NullPointerException when using `StopWatch`. |
JavaStringStartsWithSubstringCheck | .java | Checks for uses of `contains` followed by `substring`, which should be `startsWith` instead. |
JavaSystemEventAnnotationCheck | .java | Finds missing method `setDeletionSystemEventStagedModelTypes` in class with annotation @SystemEvent. |
JavaSystemExceptionCheck | .java | Finds unnecessary SystemExceptions. |
JavaTaglibMethodCheck | .java | Checks that a `*Tag` class has a `set*` and `get*` or `is*` method for each attribute. |
JavaTransactionBoundaryCheck | .java | Finds direct `add*` or `get*` calls in `*ServiceImpl` (those should use the `*service` global variable instead). |
JavaUnsafeCastingCheck | .java | Checks for potential ClassCastException. |
JavaUpgradeAlterCheck | .java | Performs several checks on `alter` calls in Upgrade classes. |
JavaUpgradeClassCheck | .java | Performs several checks on Upgrade classes. |
JavaUpgradeConnectionCheck | .java | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
JavaUpgradeIndexCheck | .java | Finds cases where the service builder indexes are updated manually in Upgrade classes. This is not needed because Liferay takes care of it. |
JavaUpgradeVersionCheck | .java | Verifies that the correct upgrade versions are used in classes that implement `UpgradeStepRegistrator`. |
JavaVariableTypeCheck | .java | Performs several checks on the modifiers on variables. |
JavaVerifyUpgradeConnectionCheck | .java | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
LFRBuildContentCheck | .lfrbuild-* | Finds `.lfrbuild*` files that are not empty. |
LPS42924Check | .java | Finds cases where `PortalUtil.getClassName*` (instead of calling `classNameLocalService` directly). |
LanguageKeysCheck | .java, .js or .jsx | Finds missing language keys in `Language.properties`. |
LocaleUtilCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where `com.liferay.portal.kernel.util.LocaleUtil` should be used (instead of `java.util.Locale`). |
LogParametersCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates the values of parameters passed to `_log.*` calls. |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDiamondOperatorCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for missing diamond operator for types that require diamond operator. |
MissingModifierCheck | .java | Verifies that a method or global variable has a modifier specified. |
NestedFieldAnnotationCheck | .java | Verifies that `NestedFieldSupport.class` is used in `service` property of `Component` annotation |
NewFileCheck | | Finds new files in directories that should not have added files. |
NullAssertionInIfStatementCheck | .java | Verifies that null check should always be first in if-statement. |
PackageinfoBNDExportPackageCheck | packageinfo | Finds legacy `packageinfo` files. |
PersistenceCallCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds illegal persistence calls across component boundaries. |
PersistenceUpdateCheck | .java | Checks that there are no stale references in service code from persistence updates. |
[PersistenceUpdateCheck](checks/persistence_update_check.markdown#persistenceupdatecheck) | .java | Checks that there are no stale references in service code from persistence updates. |
>>>>>>> LPS-144771 maven create module provider
PoshiDependenciesFileLocationCheck | .function, .macro or .testcase | Checks that dependencies files are located in the correct directory. |
PrimitiveWrapperInstantiationCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where `new Type` is used for primitive types (use `Type.valueOf` instead). |
PrincipalExceptionCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds calls to `PrincipalException.class.getName()` (use `PrincipalException.getNestedClasses()` instead). |
PropertiesArchivedModulesCheck | .eslintignore, .prettierignore or .properties | Finds `test.batch.class.names.includes` property value pointing to archived modules in `test.properties`. |
PropertiesBuildIncludeDirsCheck | .eslintignore, .prettierignore or .properties | Verifies property value of `build.include.dirs` in `build.properties`. |
PropertiesImportedFilesContentCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `imported-files.properties` file. |
PropertiesLanguageKeysCheck | .eslintignore, .prettierignore or .properties | Checks that there is no HTML markup in language keys. |
PropertiesLiferayPluginPackageFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `liferay-plugin-package.properties` file. |
PropertiesLiferayPluginPackageLiferayVersionsCheck | .eslintignore, .prettierignore or .properties | Validates the version in `liferay-plugin-package.properties` file. |
PropertiesPortalFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `portal.properties` or `portal-*.properties` file. |
PropertiesPortletFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `portlet.properties` file. |
PropertiesReleaseBuildCheck | .eslintignore, .prettierignore or .properties | Verifies that the information in `release.properties` matches the information in `ReleaseInfo.java`. |
PropertiesServiceKeysCheck | .eslintignore, .prettierignore or .properties | Finds usage of legacy properties in `service.properties`. |
PropertiesSourceFormatterContentCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `source-formatter.properties` file. |
PropertiesSourceFormatterFileCheck | .eslintignore, .prettierignore or .properties | Performs several checks on `source-formatter.properties` file. |
PropertiesVerifyPropertiesCheck | .eslintignore, .prettierignore or .properties | Finds usage of legacy properties in `portal.properties` or `system.properties`. |
ReferenceAnnotationCheck | .java | Performs several checks on classes with @Reference annotation. |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | .java | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
ResourceBundleCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no calls to `java.util.ResourceBundle.getBundle`. |
SQLLongNamesCheck | .sql | Checks for table and column names that exceed 30 characters. |
SelfReferenceCheck | .java | Finds cases of unnecessary reference to its own class. |
StaticBlockCheck | .java | Performs several checks on static blocks. |
SystemEventCheck | .java | Finds missing or redundant usage of @SystemEvent for delete events. |
TLDTypeCheck | .tld | Ensures the fully qualified name is used for types in `.tld` file. |
TestClassMissingLiferayUnitTestRuleCheck | .java | Finds missing LiferayUnitTestRule. |
TransactionalTestRuleCheck | .java | Finds usage of `TransactionalTestRule` in `*StagedModelDataHandlerTest`. |
UnparameterizedClassCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds `Class` instantation without generic type. |
UnwrappedVariableInfoCheck | .java | Finds cases where the variable should be wrapped into an inner class in order to defer array elements initialization. |
ValidatorIsNullCheck | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Ensures that only variable of type `Long`, `Serializable` or `String` is passed to method `com.liferay.portal.kernel.util.Validator.isNull`. |
XMLBuildFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `build.xml`. |
XMLCDATACheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `CDATA` inside `xml`. |
XMLCheckstyleFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `checkstyle.xml` file. |
XMLLookAndFeelCompatibilityVersionCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds missing attribute `version` in `compatibility` element in `*--look-and-feel.xml` file. |
XMLPortletFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `portlet.xml` file. |
XMLPoshiFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on poshi files. |
XMLProjectElementCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the project name in `.pom` file. |
XMLServiceEntityNameCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks that the `entity name` in `service.xml` does not equal the `package name`. |
XMLServiceFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `service.xml` file. |
XMLServiceFinderNameCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks that the `finder name` in `service.xml`. |
XMLServiceReferenceCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks for unused references in `service.xml` file. |
XMLSourcechecksFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `sourcechecks.xml` file. |
XMLSuppressionsFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `source-formatter-suppressions.xml` file. |
XMLTagAttributesCheck | .action, .function, .html, .jrxml, .macro, .path, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on tag attributes. |
XMLWebFileCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `web.xml` file. |