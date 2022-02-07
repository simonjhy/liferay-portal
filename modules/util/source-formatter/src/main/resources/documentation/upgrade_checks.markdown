# Upgrade Checks

Check | File Extensions | Description
----- | --------------- | -----------
JSPUpgradeRemovedTagsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds removed tags when upgrading. |
UpgradeCreateMavenModuleCheck | .java | Performs creating module for maven plugin project |
UpgradeCreatePluginModuleCheck | .java | Performs creating module for plugin project |
UpgradeCreateWorkspaceCheck | .java | Performs creating workspace for upgrade |
UpgradeDeprecatedAPICheck | .java | Finds calls to deprecated classes, constructors, fields or methods after an upgrade |
UpgradeJavaCheck | .java | Performs upgrade checks for `java` files |
UpgradeRemovedAPICheck | .java | Finds cases where calls are made to removed API after an upgrade. |
UpgradeWorkspacePluginVersionCheck | .java | Performs upgrade gradle workspace project plugin version |
XMLUpgradeRemovedDefinitionsCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds removed XML definitions when upgrading. |