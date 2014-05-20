package com.github.alexrichards.gradle.ricotta

import org.gradle.api.Plugin
import org.gradle.api.Project

class RicottaPlugin implements Plugin<Project> {
    @Override
    void apply(final Project project) {
        project.configure(project) {
            final RicottaConfigurationExtension ricotta = project.extensions.create 'ricotta', RicottaConfigurationExtension

            afterEvaluate {
                tasks.create('downloadTranslations') {
                    group 'Ricotta'
                    description 'Downloads Ricotta translations.'
                } << {
                    ricotta.translations.each { final RicottaTranslation translation ->
                        final File targetDirectory = translation.target.getParentFile()
                        if (!targetDirectory.exists()) {
                            targetDirectory.mkdirs()
                        } else if (translation.target.exists()) {
                            translation.target.delete()
                        }

                        final String resource = "http://${ricotta.host}/" +
                                "proj/${ricotta.projectName}/" +
                                "branch/${translation.branch}/" +
                                "lang/${translation.language}/" +
                                "templ/${translation.template}/" +
                                "subset/${translation.subset}/"

                        new URL(resource).withInputStream { is -> translation.target.withOutputStream { os -> os << is } }
                    }
                }

                tasks.create('displayTranslations') {
                    group 'Ricotta'
                    description 'Displays Ricotta configuration.'
                } << {
                    println ricotta.projectName
                    println ricotta.host

                    ricotta.translations.each { final RicottaTranslation translation ->
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
