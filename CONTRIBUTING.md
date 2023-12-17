# Contributing

## Checkstyle

Install the checkstyle plugin and set the rule with the file below.

[checkstyle.xml](settings%2Fcheckstyle%2Fcheckstyle.xml)

For the `config_loc` property, enter the path to the folder where `checkstyle-suppressions.xml` is located.

[checkstyle-suppressions.xml](settings%2Fcheckstyle%2Fcheckstyle-suppressions.xml)

## Configure Code Style

Code-Style based on [Armeria format](https://armeria.dev/community/developer-guide/#setting-up-your-ide).

### IntelliJ

[settings.jar](settings%2Fintellij_idea%2Fsettings.jar) - See [Import settings from a ZIP archive](https://www.jetbrains.com/help/idea/sharing-your-ide-settings.html#import-settings-from-a-zip-archive).
Make sure to use 'LINE OSS' code style and inspection profile.
- Go to Preferences > Editors > Code Style and set Scheme option to LINE OSS.
- Go to Preferences > Editors > Inspections and set Profile option to LINE OSS.

#### Run Tests Using IntelliJ 
Preferences > Build, Execution, Deployment > Build Tools > Gradle > Run tests using > IntelliJ IDEA
