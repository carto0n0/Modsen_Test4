Remove-Item -Recurse -Force target\allure-results,target\allure-report -ErrorAction SilentlyContinue

mvn clean test verify

allure generate target\allure-results -o target\allure-report --clean
allure open target\allure-report
