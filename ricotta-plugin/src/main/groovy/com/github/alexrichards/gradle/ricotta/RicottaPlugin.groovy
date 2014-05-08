package com.github.alexrichards.gradle.ricotta

import org.gradle.api.Plugin
import org.gradle.api.Project

class RicottaPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.configure(project) {
            final RicottaConfigurationExtension ricotta = project.extensions.create 'ricotta', RicottaConfigurationExtension

            afterEvaluate {
                tasks.create('downloadTranslations') << {
                    ricotta.translations.each { RicottaTranslation translation ->
                        final File targetDirectory = translation.target.getParentFile()
                        if (!targetDirectory.exists()) {
                            targetDirectory.mkdirs()
                        } else if (translation.target.exists()) {
                            translation.target.delete()
                        }

                        String resource = "http://${ricotta.host}/" +
                                "proj/${ricotta.projectName}/" +
                                "branch/${translation.branch}/" +
                                "lang/${translation.language}/" +
                                "templ/${translation.template}/" +
                                "subset/${translation.subset}/"

                        new URL(resource).withInputStream { is -> translation.target.withOutputStream { os -> os << is } }
                    }
                }

                tasks.create('displayTranslations') << {
                    println ricotta.projectName
                    println ricotta.host

                    ricotta.translations.each { RicottaTranslation translation ->
                        println '\t' + translation.language
                        println '\t' + translation.branch
                        println '\t' + translation.subset
                        println '\t' + translation.template
                        println '\t' + translation.target
                    }
                }
            }
        }
    }
}
