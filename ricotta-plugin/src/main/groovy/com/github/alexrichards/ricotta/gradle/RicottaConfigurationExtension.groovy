package com.github.alexrichards.ricotta.gradle

class RicottaConfigurationExtension {

    def String projectName
    def String host

    def List<RicottaTranslation> translations = []

    void projectName(final String projectName) {
        this.projectName = projectName
    }

    void host(final String host) {
        this.host = host
    }

    void translation(final Closure closure) {
        translations << (closure.delegate = new RicottaTranslation())
        closure.call()
    }
}
