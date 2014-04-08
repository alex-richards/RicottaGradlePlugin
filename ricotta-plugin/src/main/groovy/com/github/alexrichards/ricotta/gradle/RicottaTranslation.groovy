package com.github.alexrichards.ricotta.gradle

class RicottaTranslation {

    def String language
    def String branch = 'trunk'
    def String template = 'strings_android_inherit_padd'
    def String subset = 'Android'
    def File target

    void language(final String language) {
        this.language = language
    }

    void branch(final String branch) {
        this.branch = branch
    }

    void template(final String template) {
        this.template = template
    }

    void subset(final String subset) {
        this.subset = subset
    }

    void target(final File target) {
        this.target = target
    }
}
