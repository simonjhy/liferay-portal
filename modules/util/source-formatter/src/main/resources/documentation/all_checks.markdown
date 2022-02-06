# All Checks

Check | Category | File Extensions | Description
----- | -------- | --------------- | -----------
[AnnotationUseStyleCheck](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationUseStyle) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the style of elements in annotations. |
AnonymousClassCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for serialization issue when using anonymous class. |
AppendCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks instances where literal Strings are appended. |
ArquillianCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `com.liferay.arquillian.extension.junit.bridge.junit.Arquillian`. |
ArrayCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections. |
[ArrayTypeStyleCheck](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks the style of array type definitions. |
AssertEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that additional information is provided when calling `Assert.assertEquals`. |
AssignAsUsedCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds cases where an assign statement can be inlined or moved closer to where it is used. |
AttributeOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that attributes in anonymous classes are ordered alphabetically. |
[AvoidNestedBlocksCheck](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds nested blocks (blocks that are used freely in the code). |
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no import statements that use the * notation. |
BNDBundleActivatorCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates property value for `Bundle-Activator`. |
BNDBundleCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates `Liferay-Releng-*` properties. |
BNDBundleInformationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName`. |
BNDCapabilityCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Sorts and applies logic to fix line breaks to property values for `Provide-Capability` and `Require-Capability`. |
BNDDefinitionKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates definition keys in `.bnd` files. |
BNDDeprecatedAppBNDsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .bnd | Checks for redundant `app.bnd` in deprecated or archived modules. |
BNDDirectoryNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks if the directory names of the submodules match the parent module name. |
BNDExportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages. |
BNDImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Sorts class names and checks for use of wildcards in property values for `-conditionalpackage`, `-exportcontents` and `Export-Package`. |
BNDIncludeResourceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for unnesecarry including of `test-classes/integration`. |
BNDLiferayEnterpriseAppCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for correct use of property `Liferay-Enterprise-App`. |
BNDLiferayRelengBundleCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks if `.lfrbuild-release-src` file exists for DXP module with `Liferay-Releng-Bundle: true` |
BNDLiferayRelengCategoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates `Liferay-Releng-Category` properties |
BNDLineBreaksCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Finds missing and unnecessary line breaks in `.bnd` files. |
BNDMultipleAppBNDsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module). |
BNDRangeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for use or range expressions. |
BNDRunInstructionsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .bndrun | Sorts definition keys alphabetically. |
BNDSchemaVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion`. |
BNDStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Applies rules to enforce consisteny in code style. |
BNDSuiteCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .bnd | Checks that deprecated apps are moved to the `archived` folder. |
BNDWebContextPathCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory. |
BNDWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Finds missing and unnecessary whitespace in `.bnd` files. |
CDNCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | | Checks the URL in `artifact.properties` files. |
CQLKeywordCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .cql | Checks that Cassandra keywords are upper case. |
CSSCommentsCheck | [Styling](styling_checks.markdown#styling-checks) | .css or .scss | Validates comments in `.css` files. |
CSSImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .css or .scss | Sorts and groups imports in `.css` files. |
CSSPropertiesOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .css or .scss | Sorts properties in `.css` files. |
CamelCaseNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks variable names for correct use of `CamelCase`. |
ChainingCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that chaining is only applied on certain types and methods. |
CodeownersFileLocationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | CODEOWNERS | Checks that `CODEOWNERS` files are located in `.github` directory. |
CodeownersWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | CODEOWNERS | Finds missing and unnecessary whitespace in `CODEOWNERS` files. |
CompanyIterationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `CompanyLocalService.forEachCompany` or `CompanyLocalService.forEachCompanyId` is used when iterating over companies |
CompatClassImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that classes are imported from `compat` modules, when possible. |
ConcatCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for correct use of `StringBundler.concat`. |
ConfigDefinitionKeysCheck | [Styling](styling_checks.markdown#styling-checks) | .cfg or .config | Sorts definition keys in `.config` files. |
ConstantNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that variable names of constants follow correct naming rules. |
ConstructorGlobalVariableDeclarationCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that initial values of global variables are not set in the constructor. |
ConstructorMissingEmptyLineCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks for line breaks when assiging variables in constructor. |
ConsumerTypeAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with @ConsumerType annotation. |
ContractionsCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds contractions in Strings (such as `can't` or `you're`). |
CopyrightCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates `copyright` header. |
CreationMenuBuilderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Checks that `CreationMenuBuilder` is used when possible. |
DTOEnumCreationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks the creation of DTO enum. |
[DefaultComesLastCheck](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that the `default` is after all the cases in a `switch` statement. |
DeprecatedAPICheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds calls to deprecated classes, constructors, fields or methods. |
DockerfileEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | Dockerfile | Finds missing and unnecessary empty lines. |
DockerfileInstructionCheck | [Styling](styling_checks.markdown#styling-checks) | Dockerfile | Performs styling rules on instructions in `Dockerfile` files. |
EmptyCollectionCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no calls to `Collections.EMPTY_LIST`, `Collections.EMPTY_MAP` or `Collections.EMPTY_SET`. |
EmptyConstructorCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds unnecessary empty constructors. |
EnumConstantDividerCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Find unnecessary empty lines between enum constants. |
EnumConstantOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of enum constants. |
EqualClauseIfStatementsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds consecutive if-statements with identical clauses. |
ExceptionCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds private methods that throw unnecessary exception. |
ExceptionMessageCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates messages that are passed to exceptions. |
ExceptionVariableNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Validates variable names that have type `*Exception`. |
FTLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds missing and unnecessary empty lines. |
FTLIfStatementCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds incorrect use of parentheses in statement. |
FTLImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Sorts and groups imports in `.ftl` files. |
FTLLiferayVariableOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Sorts assign statement of `liferay_*` variables. |
FTLStringRelationalOperatorCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds cases of `==` or `!=` where `stringUtil.equals`, `validator.isNotNull` or `validator.isNull` can be used instead. |
FTLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Applies rules to enforce consisteny in code style. |
FTLTagAttributesCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Sorts and formats attributes values in tags. |
FTLTagCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds cases where consecutive `#assign` can be combined. |
FTLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .ftl | Finds missing and unnecessary whitespace in `.ftl` files. |
FactoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where `*Factory` should be used when creating new instances of an object. |
FilterStringWhitespaceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing and unnecessary whitespace in the value of the filter string in `ServiceTrackerFactory.open` or `WaiterUtil.waitForFilter`. |
FrameworkBundleCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used. |
FullyQualifiedNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds cases where a Fully Qualified Name is used instead of importing a class. |
GenericTypeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that generics are always specified to provide compile-time checking and removing the risk of `ClassCastException` during runtime. |
GetterUtilCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where the default value is passed to `GetterUtil.get*` or `ParamUtil.get*`. |
GradleBlockOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Sorts logic in gradle build files. |
GradleBodyCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Applies rules to enforce consisteny in the body of gradle build files. |
GradleDependenciesCheck | [Performance](performance_checks.markdown#performance-checks) | .gradle | Checks that modules are not depending on other modules. |
GradleDependencyArtifactsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks that value `default` is not used for attribute `version`. |
GradleDependencyConfigurationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates the scope of dependencies in build gradle files. |
GradleDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks the version for dependencies in gradle build files. |
GradleExportedPackageDependenciesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates dependencies in gradle build files. |
GradleImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Sorts and groups imports in `.gradle` files. |
GradleIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Finds incorrect indentation in gradle build files. |
GradleJavaVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks values of properties `sourceCompatibility` and `targetCompatibility` in gradle build files. |
GradlePropertiesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates property values in gradle build files. |
GradleProvidedDependenciesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates the scope of dependencies in build gradle files. |
GradleRequiredDependenciesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Validates the dependencies in `/required-dependencies/required-dependencies/build.gradle`. |
GradleStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Applies rules to enforce consisteny in code style. |
GradleTaskCreationCheck | [Styling](styling_checks.markdown#styling-checks) | .gradle | Checks that a task is declared on a separate line before the closure. |
GradleTestDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks the version for dependencies in gradle build files. |
HTMLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .html or .path | Finds missing and unnecessary empty lines. |
HTMLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .html or .path | Finds missing and unnecessary whitespace in `.html` files. |
IfStatementCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds empty if-statements and consecutive if-statements with identical bodies |
IncorrectFileLocationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | | Checks that `/src/*/java/` only contains `.java` files. |
InstanceofOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Check the order of `instanceof` calls. |
ItemBuilderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Checks that `DropdownItemBuilder`, `LabelItemBuilder` or `NavigationItemBuilder` is used when possible. |
ItemListBuilderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Checks that `DropdownItemListBuilder`, `LabelItemListBuilder` or `NavigationItemListBuilder` is used when possible. |
JSLodashDependencyCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .js or .jsx | Finds incorrect use of `AUI._`. |
JSONDeprecatedPackagesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .ipynb, .json or .npmbridgerc | Finds incorrect use of deprecated packages in `package.json` files. |
JSONNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if variable names follow naming conventions. |
JSONPackageJSONBNDVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .ipynb, .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
JSONPackageJSONCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .ipynb, .json or .npmbridgerc | Checks content of `package.json` files. |
JSONPackageJSONDependencyVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .ipynb, .json or .npmbridgerc | Checks the version for dependencies in `package.json` files. |
JSONStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .ipynb, .json or .npmbridgerc | Applies rules to enforce consisteny in code style. |
JSONUtilCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for utilization of class `JSONUtil`. |
JSONValidationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .ipynb, .json or .npmbridgerc | Validates content of `.json` files. |
JSPArrowFunctionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no array functions. |
JSPCoreTaglibCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where a `c:choose` or `c:if` tag can be used instead of an if-statement. |
JSPDefineObjectsCheck | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for unnesecarry duplication of code that already exists in `defineObjects`. |
JSPEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds missing and unnecessary empty lines. |
JSPExceptionOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks the order of exceptions in `.jsp` files. |
JSPFileNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if the file name of `.jsp` or `.jspf` follows the naming conventions. |
JSPFunctionNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Check if the names of functions in `.jsp` files follow naming conventions. |
JSPIllegalSyntaxCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of `System.out.print`, `console.log` or `debugger.*` in `.jsp` files. |
JSPImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Sorts and groups imports in `.jsp` files. |
JSPIncludeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates values of `include` in `.jsp` files. |
JSPIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect indentation in `.jsp` files. |
JSPInlineVariableCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where variables can be inlined. |
JSPJavaParserCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Performs JavaParser on `.java` files. |
JSPLanguageKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds missing language keys in `Language.properties`. |
JSPLanguageUtilCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where Locale is passed to `LanguageUtil.get` instead of `HttpServletRequest`. |
JSPLineBreakCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds missing and unnecessary line breaks in `.jsp` lines. |
JSPLogFileNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates the value that is passed to `LogFactoryUtil.getLog` in `.jsp`. |
JSPMethodCallsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that type `LiferayPortletResponse` is used to call `getNamespace()`. |
JSPMissingTaglibsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for missing taglibs. |
JSPModuleIllegalImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of `com.liferay.registry.Registry` or `com.liferay.util.ContentUtil`. |
JSPParenthesesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of parentheses in statement. |
JSPRedirectBackURLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates values of variable `redirect`. |
JSPSendRedirectCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no calls to `HttpServletResponse.sendRedirect` from `jsp` files. |
JSPServiceUtilCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of `*ServiceUtil` in `.jsp` files in modules. |
JSPSessionKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
JSPStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Applies rules to enforce consisteny in code style. |
JSPTagAttributesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Performs several checks on tag attributes. |
JSPTaglibVariableCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if variable names follow naming conventions. |
JSPUnusedJSPFCheck | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds `.jspf` files that are not used. |
JSPUnusedTermsCheck | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds taglibs, variables and imports that are unused. |
JSPUpgradeRemovedTagsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds removed tags when upgrading. |
JSPVarNameCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that values of attribute `var` follow naming conventions. |
JSPVariableOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if variable names are in alphabetical order. |
JSPWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds missing and unnecessary whitespace in `.jsp` files. |
JSPXSSVulnerabilitiesCheck | [Security](security_checks.markdown#security-checks) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds xss vulnerabilities. |
JSStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .js or .jsx | Applies rules to enforce consisteny in code style. |
JSWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .js or .jsx | Finds missing and unnecessary whitespace in `.js` files. |
Java2HTMLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds incorrect use of `.java.html` in `.jsp` files. |
JavaAPISignatureCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures. |
JavaAbstractMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds incorrect `abstract` methods in `interface`. |
JavaAggregateTestRuleParameterOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of parameters in `new AggregateTestRule` calls. |
JavaAnnotationDefaultAttributeCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds cases where the default value is passed to annotations in package `*.bnd.annotations` or `*.bind.annotations`. |
JavaAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on annotations. |
JavaAnonymousInnerClassCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on anonymous classes. |
JavaAssertEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Validates `Assert.assertEquals` calls. |
JavaBaseUpgradeCallableCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that BaseUpgradeCallable is used instead of Callable or Runnable in Upgrade and Verify classes. |
JavaBooleanStatementCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on variable declaration of type `Boolean`. |
JavaBooleanUsageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds incorrect use of passing boolean values in `setAttribute` calls. |
JavaClassNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks if class names follow naming conventions. |
JavaCleanUpMethodSuperCleanUpCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that `cleanUp` method in `*Tag` class with `@Override` annotation calls the `cleanUp` method of the superclass. |
JavaCleanUpMethodVariablesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that variables in `Tag` classes get cleaned up properly. |
JavaCollapseImportsCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Collapses imports that use wildcard |
JavaCollatorUtilCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `Collator`. |
JavaComponentActivateCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks if methods with annotation `@Activate` or `@Deactivate` follow naming conventions. |
JavaComponentAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with `@Component` annotation. |
JavaConfigurationAdminCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration`. |
JavaConfigurationCategoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web`. |
JavaConstructorParametersCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that the order of variable assignments matches the order of the parameters in the constructor signature. |
JavaConstructorSuperCallCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds unnecessary call to no-argument constructor of the superclass. |
JavaDeprecatedJavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Checks if the `@deprecated` javadoc is pointing to the correct version. |
JavaDeprecatedKernelClassesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds calls to deprecated classes `com.liferay.portal.kernel.util.CharPool` and `com.liferay.portal.kernel.util.StringPool`. |
JavaDeserializationSecurityCheck | [Security](security_checks.markdown#security-checks) | .java | Finds Java serialization vulnerabilities. |
JavaDiamondOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds cases where Diamond Operator is not used. |
JavaDuplicateVariableCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds variables where a variable with the same name already exists in an extended class. |
JavaElseStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds unnecessary `else` statements (when the `if` statement ends with a `return` statement). |
JavaEmptyLineAfterSuperCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds missing emptly line after a `super` call. |
JavaEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds missing and unnecessary empty lines. |
JavaExceptionCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that variable names of exceptions in `catch` statements follow naming conventions. |
JavaFinalVariableCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds cases of unneeded `final` modifiers for variables and parameters. |
JavaFinderCacheCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath`. |
JavaFinderImplCustomSQLCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that hardcoded SQL values in `*FinderImpl` classes match the SQL in the `.xml` file in the `custom-sql` directory. |
JavaForLoopCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks if a Enhanced For Loop can be used instead of a Simple For Loop. |
JavaHelperUtilCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Finds incorrect use of `*Helper` or `*Util` classes. |
JavaHibernateSQLCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds calls to `com.liferay.portal.kernel.dao.orm.Session.createSQLQuery` (use `Session.createSynchronizedSQLQuery` instead). |
JavaIOExceptionCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Validates use of `IOException`. |
JavaIgnoreAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds methods with `@Ignore` annotation in test classes. |
JavaIllegalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases of incorrect use of certain classes. |
JavaImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Sorts and groups imports in `.java` files. |
JavaIndexableCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the type gets returned when using annotation `@Indexable`. |
JavaInnerClassImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds cases where inner classes are imported. |
JavaInterfaceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that `interface` is not `static`. |
JavaInternalPackageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on class in `internal` package. |
JavaJSPDynamicIncludeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on `*JSPDynamicInclude` class. |
JavaLocalSensitiveComparisonCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that `java.text.Collator` is used when comparing localized values. |
JavaLogClassNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks the name of the class that is passed in `LogFactoryUtil.getLog`. |
JavaLogLevelCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the correct log messages are printed. |
JavaLongLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds lines that are longer than the specified maximum line length. |
JavaMapBuilderGenericsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing or unnecessary generics on `*MapBuilder.put` calls. |
JavaMetaAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta`. |
JavaMissingOverrideCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing @Override annotations. |
JavaMissingXMLPublicIdsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing public IDs for check XML files. |
JavaModifiedServiceMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing empty lines before `removedService` or `addingService` calls. |
JavaModuleComponentCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for use of `@Component` in `-api` or `-spi` modules. |
JavaModuleExposureCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for exposure of `SPI` types in `API`. |
JavaModuleIllegalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases of incorrect use of certain classes in modules. |
JavaModuleInternalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where a module imports an `internal` class from another class. |
JavaModuleJavaxPortletInitParamTemplatePathCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Validates the value of `javax.portlet.init-param.template-path`. |
JavaModuleServiceProxyFactoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases of `ServiceProxyFactory.newServiceTrackedInstance`. |
JavaModuleServiceReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where `@BeanReference` annotation should be used instead of `@ServiceReference` annotation. |
JavaModuleTestCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks package names in tests. |
JavaMultiPlusConcatCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that we do not concatenate more than 3 String objects. |
JavaOSGiReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with `@Component` annotation. |
JavaPackagePathCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the package name matches the file location. |
JavaProcessCallableCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID`. |
JavaProviderTypeAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with `@ProviderType` annotation. |
JavaRedundantConstructorCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds unnecessary empty constructor. |
JavaReleaseInfoCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Validates information in `ReleaseInfo.java`. |
JavaResultSetCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use `java.sql.ResultSet.getInt(int)`. |
JavaReturnStatementCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds unnecessary `else` statement (when `if` and `else` statement both end with `return` statement). |
JavaSeeAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for nested annotations inside `@see`. |
JavaServiceImplCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Ensures that `afterPropertiesSet` and `destroy` methods in `*ServiceImpl` always call the method with the same name in the superclass. |
JavaServiceObjectCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks for correct use of `*.is*` instead of `*.get*` when calling methods generated by ServiceBuilder. |
JavaServiceTrackerFactoryCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that there are no calls to deprecatred method `ServiceTrackerFactory.open(java.lang.Class)`. |
JavaServiceUtilCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no calls to `*ServiceImpl` from a `*ServiceUtil` class. |
JavaSessionCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds unnecessary calls to `Session.flush()` (calls that are followed by `Session.clear()`). |
JavaSignatureParametersCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of parameters. |
JavaSourceFormatterDocumentationCheck | [Documentation](documentation_checks.markdown#documentation-checks) | .java | Finds SourceFormatter checks that have no documentation. |
JavaStagedModelDataHandlerCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing method `setMvccVersion` in class extending `BaseStagedModelDataHandler` in module that has `mvcc-enabled=true` in `service.xml`. |
JavaStaticBlockCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on `static` blocks. |
JavaStaticImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that there are no static imports. |
JavaStaticMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where methods are unncessarily declared static. |
JavaStaticVariableDependencyCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that static variables in the same class that depend on each other are correctly defined. |
JavaStopWatchCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for potential NullPointerException when using `StopWatch`. |
JavaStringBundlerConcatCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds calls to `StringBundler.concat` with less than 3 parameters. |
JavaStringBundlerInitialCapacityCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Checks the initial capacity of new instances of `StringBundler`. |
JavaStringStartsWithSubstringCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for uses of `contains` followed by `substring`, which should be `startsWith` instead. |
JavaStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Applies rules to enforce consisteny in code style. |
JavaSwitchCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that `if/else` statement is used instead of `switch` statement. |
JavaSystemEventAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing method `setDeletionSystemEventStagedModelTypes` in class with annotation @SystemEvent. |
JavaSystemExceptionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds unnecessary SystemExceptions. |
JavaTaglibMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that a `*Tag` class has a `set*` and `get*` or `is*` method for each attribute. |
JavaTermDividersCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds missing or unnecessary empty lines between javaterms. |
JavaTermOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the order of javaterms. |
JavaTermStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Applies rules to enforce consisteny in code style. |
JavaTestMethodAnnotationsCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks if methods with test annotations follow the naming conventions. |
JavaTransactionBoundaryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds direct `add*` or `get*` calls in `*ServiceImpl` (those should use the `*service` global variable instead). |
JavaUnsafeCastingCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for potential ClassCastException. |
JavaUnusedSourceFormatterChecksCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Finds `*Check` classes that are not configured. |
JavaUpgradeAlterCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on `alter` calls in Upgrade classes. |
JavaUpgradeClassCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on Upgrade classes. |
JavaUpgradeConnectionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
JavaUpgradeIndexCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where the service builder indexes are updated manually in Upgrade classes. This is not needed because Liferay takes care of it. |
JavaUpgradeVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Verifies that the correct upgrade versions are used in classes that implement `UpgradeStepRegistrator`. |
JavaVariableTypeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on the modifiers on variables. |
JavaVerifyUpgradeConnectionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
JavaXMLSecurityCheck | [Security](security_checks.markdown#security-checks) | .java | Finds possible XXE or Quadratic Blowup security vulnerabilities. |
JavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Performs several checks on javadoc. |
[JavadocStyleCheck](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocStyle) | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Validates Javadoc comments to help ensure they are well formed. |
LFRBuildContentCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .lfrbuild-* | Finds `.lfrbuild*` files that are not empty. |
LFRBuildReadmeCheck | [Documentation](documentation_checks.markdown#documentation-checks) | .lfrbuild-* | Checks that `.lfrbuild*` files are documented in a marker file. |
LPS42924Check | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where `PortalUtil.getClassName*` (instead of calling `classNameLocalService` directly). |
LambdaCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `lambda` statements are as simple as possible. |
LanguageKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .js or .jsx | Finds missing language keys in `Language.properties`. |
ListUtilCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for utilization of class `ListUtil`. |
LiteralStringEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where `Objects.equals` should be used. |
[LocalFinalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalFinalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that local final variable names conform to a specified pattern. |
LocalPatternCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that a `java.util.Pattern` variable is declared globally, so that it is initiated only once. |
[LocalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that local, non-final variable names conform to a specified pattern. |
LocaleUtilCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where `com.liferay.portal.kernel.util.LocaleUtil` should be used (instead of `java.util.Locale`). |
LogMessageCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Validates messages that are passed to `log.*` calls. |
LogParametersCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates the values of parameters passed to `_log.*` calls. |
MVCCommandNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks for consistent naming for values of `mvc.command.name`. |
MapBuilderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `ConcurrentHashMapBuilder`, `HashMapBuilder`, `LinkedHashMapBuilder` or `TreeMapBuilder` is used when possible. |
MapIterationCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no unnecessary map iterations. |
MarkdownFileExtensionCheck | [Styling](styling_checks.markdown#styling-checks) | .markdown or .md | Finds `markdown` files with `.md` extension (use `.markdown`). |
MarkdownSourceFormatterDocumentationCheck | [Documentation](documentation_checks.markdown#documentation-checks) | .markdown or .md | Validates the header of Source Formatter documentation files. |
MarkdownSourceFormatterReadmeCheck | [Documentation](documentation_checks.markdown#documentation-checks) | .markdown or .md | Generates Source Formatter documentation index files. |
MarkdownStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .markdown or .md | Applies rules to enforce consisteny in code style. |
MarkdownWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .markdown or .md | Finds missing and unnecessary whitespace in `.markdown` files. |
[MemberNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MemberName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .jspx, .jspx, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that instance variable names conform to a specified pattern. |
MethodCallsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Sorts method calls for certain object (for example, `put` calls in `java.util.HashMap`). |
[MethodNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MethodName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .jspx, .jspx, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that method names conform to a specified pattern. |
MethodNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that method names follow naming conventions. |
[MethodParamPadCheck](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks the padding between the identifier of a method definition, constructor definition, method call, or constructor invocation; and the left parenthesis of the parameter list. |
MissingAuthorCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Finds classes that have no `@author` specified. |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDeprecatedJavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDiamondOperatorCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for missing diamond operator for types that require diamond operator. |
MissingEmptyLineCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for missing line breaks around variable declarations. |
MissingModifierCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Verifies that a method or global variable has a modifier specified. |
MissingParenthesesCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds missing parentheses in conditional statement. |
[ModifierOrderCheck](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that the order of modifiers conforms to the suggestions in the Java Language specification, § 8.1.1, 8.3.1, 8.4.3 and 9.4. |
[MultipleVariableDeclarationsCheck](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that each variable declaration is in its own statement and on its own line. |
NestedFieldAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Verifies that `NestedFieldSupport.class` is used in `service` property of `Component` annotation |
NestedIfStatementCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds nested if statements that can be combined. |
NewFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | | Finds new files in directories that should not have added files. |
[NoLineWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoLineWrap) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that chosen statements are not line-wrapped. |
[NoWhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there is no whitespace after a token. |
[NoWhitespaceBeforeCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there is no whitespace before a token. |
NonbreakingSpaceCheck | [Styling](styling_checks.markdown#styling-checks) | | Finds `no break space` (`\u00a0`) characters. |
NotRequireThisCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds cases of unnecessary use of `this.`. |
NullAssertionInIfStatementCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Verifies that null check should always be first in if-statement. |
NumberSuffixCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Verifies that uppercase `D`, `F`, or `L` is used when denoting Double/Float/Long. |
OSGiResourceBuilderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Avoid using *Resource.builder. |
[OneStatementPerLineCheck](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there is only one statement per line. |
OperatorOperandCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Verifies that operand do not go over too many lines and make the operator hard to read. |
OperatorOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Verifies that when an operator has a literal string or a number as one of the operands, it is always on the right hand side. |
[OperatorWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks the policy on how to wrap lines on operators. |
[PackageNameCheck](https://checkstyle.sourceforge.io/config_naming.html#PackageName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that package names conform to a specified pattern. |
PackageinfoBNDExportPackageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | packageinfo | Finds legacy `packageinfo` files. |
[ParameterNameCheck](https://checkstyle.sourceforge.io/config_naming.html#ParameterName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that method parameter names conform to a specified pattern. |
ParsePrimitiveTypeCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Verifies that `GetterUtil.parse*` is used to parse primitive types, when possible. |
PersistenceCallCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds illegal persistence calls across component boundaries. |
PersistenceUpdateCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no stale references in service code from persistence updates. |
PlusStatementCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Performs several checks to statements where `+` is used for concatenation. |
PortletURLBuilderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `PortletURLBuilder` is used when possible. |
[PortletURLBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `PortletURLBuilder` is used when possible. |
>>>>>>> LPS-144771 maven create module provider
PoshiDependenciesFileLocationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .function, .macro or .testcase | Checks that dependencies files are located in the correct directory. |
PoshiStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Applies rules to enforce consisteny in code style. |
PoshiVariableNameCheck | [Styling](styling_checks.markdown#styling-checks) | .function, .macro or .testcase | Checks variable names for correct use of `camelCase`. |
PrimitiveWrapperInstantiationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where `new Type` is used for primitive types (use `Type.valueOf` instead). |
PrincipalExceptionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds calls to `PrincipalException.class.getName()` (use `PrincipalException.getNestedClasses()` instead). |
PropertiesArchivedModulesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Finds `test.batch.class.names.includes` property value pointing to archived modules in `test.properties`. |
PropertiesBuildIncludeDirsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Verifies property value of `build.include.dirs` in `build.properties`. |
PropertiesCommentsCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Validates comments in `.properties` files. |
PropertiesDefinitionKeysCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Sorts definition keys in `liferay-plugin-package.properties` file. |
PropertiesDependenciesFileCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Sorts the properties in `dependencies.properties` file. |
PropertiesEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Finds missing and unnecessary empty lines. |
PropertiesImportedFilesContentCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Performs several checks on `imported-files.properties` file. |
PropertiesLanguageKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Checks that there is no HTML markup in language keys. |
PropertiesLanguageKeysOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Sort language keys in `Language.properties` file. |
PropertiesLiferayPluginPackageFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Performs several checks on `liferay-plugin-package.properties` file. |
PropertiesLiferayPluginPackageLiferayVersionsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Validates the version in `liferay-plugin-package.properties` file. |
PropertiesLongLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Finds lines that are longer than the specified maximum line length. |
PropertiesMultiLineValuesOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Verifies that property with multiple values is not on a single line. |
PropertiesPortalEnvironmentVariablesCheck | [Documentation](documentation_checks.markdown#documentation-checks) | .eslintignore, .prettierignore or .properties | Verifies that the environment property in the documentation matches the property name. |
PropertiesPortalFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Performs several checks on `portal.properties` or `portal-*.properties` file. |
PropertiesPortletFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Performs several checks on `portlet.properties` file. |
PropertiesReleaseBuildCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Verifies that the information in `release.properties` matches the information in `ReleaseInfo.java`. |
PropertiesServiceKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Finds usage of legacy properties in `service.properties`. |
PropertiesSourceFormatterContentCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Performs several checks on `source-formatter.properties` file. |
PropertiesSourceFormatterFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Performs several checks on `source-formatter.properties` file. |
PropertiesStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Applies rules to enforce consisteny in code style. |
PropertiesVerifyPropertiesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .eslintignore, .prettierignore or .properties | Finds usage of legacy properties in `portal.properties` or `system.properties`. |
PropertiesWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .eslintignore, .prettierignore or .properties | Finds missing and unnecessary whitespace in `.properties` files. |
PythonClassesAndMethodsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .py | Checks the order of classes and methods. |
PythonImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .py | Sorts and groups imports in `.py` files. |
PythonStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .py | Applies rules to enforce consisteny in code style. |
PythonWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .py | Finds missing and unnecessary whitespace. |
RedundantBranchingStatementCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds unnecessary branching (`break`, `continue` or `return`) statements. |
ReferenceAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on classes with @Reference annotation. |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
ResourceBundleCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no calls to `java.util.ResourceBundle.getBundle`. |
SQLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .sql | Finds missing and unnecessary empty lines. |
SQLLongNamesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .sql | Checks for table and column names that exceed 30 characters. |
SQLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .sql | Applies rules to enforce consisteny in code style. |
SelfReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases of unnecessary reference to its own class. |
SemiColonCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases of unnecessary semicolon. |
SessionKeysCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
SetUtilMethodsCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases of inefficient SetUtil operations. |
SingleStatementClauseCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Verifies that `for`, `if` or `while` statement always uses curly braces. |
SizeIsZeroCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases of calls like `list.size() == 0` (use `list.isEmpty()` instead). |
SlantedQuotesCheck | [Styling](styling_checks.markdown#styling-checks) | | Finds `slanted quote` (`\u201a`, `\u201b`, `\u201c`, `\u201d`, `\u201e`, `\u201f`, `\u2018` or `\u2019`) characters. |
SoyEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .soy | Finds missing and unnecessary empty lines. |
StaticBlockCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on static blocks. |
[StaticVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#StaticVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .jspx, .jspx, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that static, non-final variable names conform to a specified pattern. |
StringBundlerNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks for consistent naming on variables of type 'StringBundler'. |
StringCastCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases where a redundant `toString()` is called on variable type `String`. |
[StringLiteralEqualityCheck](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that string literals are not used with == or !=. |
StringMethodsCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if performance can be improved by using different String operation methods. |
SubstringCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds cases like `s.substring(1, s.length())` (use `s.substring(1)` instead). |
SystemEventCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing or redundant usage of @SystemEvent for delete events. |
TLDElementOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .tld | Checks the order of attributers in `.tld` file. |
TLDTypeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .tld | Ensures the fully qualified name is used for types in `.tld` file. |
TXTEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .txt | Finds missing and unnecessary empty lines. |
TXTStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .txt | Applies rules to enforce consisteny in code style. |
TernaryOperatorCheck | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds use of ternary operator in `java` files (use if statement instead). |
TestClassCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that names of test classes follow naming conventions. |
TestClassMissingLiferayUnitTestRuleCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds missing LiferayUnitTestRule. |
ThreadLocalUtilCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds new instances of `java.lang.Thread` (use `ThreadLocalUtil.create` instead). |
ThreadNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that names of threads follow naming conventions. |
TransactionalTestRuleCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds usage of `TransactionalTestRule` in `*StagedModelDataHandlerTest`. |
TryWithResourcesCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Ensures using Try-With-Resources statement to properly close the resource. |
[TypeNameCheck](https://checkstyle.sourceforge.io/config_naming.html#TypeName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that type names conform to a specified pattern. |
UnicodePropertiesBuilderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | Checks that `UnicodePropertiesBuilder` is used when possible. |
UnnecessaryAssignCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds unnecessary assign statements (when it is either reassigned or returned right after). |
UnnecessaryMethodCallCheck | [Styling](styling_checks.markdown#styling-checks) | .java | Finds unnecessary method calls. |
[UnnecessaryParenthesesCheck](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks if unnecessary parentheses are used in a statement or expression. |
UnnecessaryTypeCastCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds unnecessary Type Casting. |
UnnecessaryVariableDeclarationCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds unnecessary variable declarations (when it is either reassigned or returned right after). |
UnparameterizedClassCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds `Class` instantation without generic type. |
UnprocessedExceptionCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds cases where an `Exception` is swallowed without being processed. |
UnusedMethodCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds private methods that are not used. |
UnusedParameterCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds parameters in private methods that are not used. |
UnusedVariableCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds variables that are declared, but not used. |
UnwrappedVariableInfoCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Finds cases where the variable should be wrapped into an inner class in order to defer array elements initialization. |
UpgradeDeprecatedAPICheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | .java | Finds calls to deprecated classes, constructors, fields or methods after an upgrade |
UpgradeJavaCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | .java | Performs upgrade checks for `java` files |
UpgradeRemovedAPICheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | .java | Finds cases where calls are made to removed API after an upgrade. |
ValidatorEqualsCheck | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that there are no calls to `Validator.equals(Object, Object)`. |
ValidatorIsNullCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Ensures that only variable of type `Long`, `Serializable` or `String` is passed to method `com.liferay.portal.kernel.util.Validator.isNull`. |
VariableDeclarationAsUsedCheck | [Performance](performance_checks.markdown#performance-checks) | .java | Finds cases where a variable declaration can be inlined or moved closer to where it is used. |
VariableNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that variable names follow naming conventions. |
[WhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that a token is followed by whitespace, with the exception that it does not check for whitespace after the semicolon of an empty for iterator. |
[WhitespaceAroundCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that a token is surrounded by whitespace. |
WhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .cfg, .config, .cql, .css, .dtd, .expect, .gradle, .groovy, .scss, .sh, .soy, .sql, .tld, .ts, .tsx, Dockerfile or packageinfo | Finds missing and unnecessary whitespace. |
XMLBuildFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `build.xml`. |
XMLCDATACheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `CDATA` inside `xml`. |
XMLCheckstyleFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `checkstyle.xml` file. |
XMLCustomSQLOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of attributes in `custom-sql` file. |
XMLCustomSQLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Applies rules to enforce consisteny in code style for `.xml` files in directory `custom-sql`. |
XMLDDLStructuresFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of attributes in `-structures.xml` file. |
XMLDTDVersionCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the DTD version in `*.xml` file. |
XMLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds missing and unnecessary empty lines. |
XMLFSBExcludeFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of attributes in `fsb-exclude.xml` file. |
XMLFriendlyURLRoutesFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `*-routes.xml` file. |
XMLHBMFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of imports in `*-hbm.xml` file. |
XMLImportsCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Sorts and groups imports in `.xml` files. |
XMLIndentationCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .tld, .toggle, .tpl, .wsdl, .xml or .xsd | Finds incorrect indentation in `.xml` files. |
XMLIvyFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of dependcies in `ivy.xml` file. |
XMLLog4jFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of categories in `*-log4j.xml` file. |
XMLLookAndFeelCompatibilityVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds missing attribute `version` in `compatibility` element in `*--look-and-feel.xml` file. |
XMLLookAndFeelFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of attributes in `*--look-and-feel.xml` file. |
XMLModelHintsFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of attributes in `*-model-hints.xml` file. |
XMLPomFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of dependencies in `pom.xml` file. |
XMLPortletFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `portlet.xml` file. |
XMLPortletPreferencesFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of elements in files in directory `resource-actions`. |
XMLPoshiFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on poshi files. |
XMLProjectElementCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the project name in `.pom` file. |
XMLResourceActionsFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of elements in files in directory `resource-actions`. |
XMLServiceEntityNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks that the `entity name` in `service.xml` does not equal the `package name`. |
XMLServiceFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `service.xml` file. |
XMLServiceFinderNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks that the `finder name` in `service.xml`. |
XMLServiceReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks for unused references in `service.xml` file. |
XMLSourcechecksFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `sourcechecks.xml` file. |
XMLSpringFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of elements in `*-spring.xml` file. |
XMLStrutsConfigFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of elements in `struts-config.xml` file. |
XMLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Applies rules to enforce consisteny in code style. |
XMLSuppressionsFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `source-formatter-suppressions.xml` file. |
XMLTagAttributesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .html, .jrxml, .macro, .path, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on tag attributes. |
XMLTestIgnorableErrorLinesFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of elements in `test-ignorable-error-lines.xml` file. |
XMLTilesDefsFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of elements in `tiles-defs.xml` file. |
XMLToggleFileCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the order of elements in `.toggle` file. |
XMLUpgradeRemovedDefinitionsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds removed XML definitions when upgrading. |
XMLWebFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Performs several checks on `web.xml` file. |
XMLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds missing and unnecessary whitespace in `.xml` files. |
XMLWorkflowDefinitionFileNameCheck | [Styling](styling_checks.markdown#styling-checks) | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks the file name of workflow definition files. |
YMLDefinitionOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Sorts definitions alphabetically in `.yml` files. |
YMLEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Finds missing and unnecessary empty lines. |
YMLLongLinesCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Finds lines that are longer than the specified maximum line length. |
YMLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Applies rules to enforce consisteny in code style. |
YMLWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .yaml or .yml | Finds missing and unnecessary whitespace in `.yml` files. |