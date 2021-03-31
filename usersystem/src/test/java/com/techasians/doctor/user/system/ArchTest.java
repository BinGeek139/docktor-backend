package com.techasians.doctor.user.system;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.techasians.doctor.user.system");

        noClasses()
            .that()
            .resideInAnyPackage("com.techasians.doctor.user.system.service..")
            .or()
            .resideInAnyPackage("com.techasians.doctor.user.system.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.techasians.doctor.user.system.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
