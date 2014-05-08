import com.github.alexrichards.gradle.ricotta.RicottaConfigurationExtension
import com.github.alexrichards.gradle.ricotta.RicottaTranslation
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test

class ConfigurationTest {

    @Test
    void testDelegate() {
        Project project = ProjectBuilder.builder().build()

        project.apply plugin: 'ricotta'
        Assert.assertTrue project.ricotta instanceof RicottaConfigurationExtension

        project.ricotta {
            projectName 'test name'
            host 'test host'
            translation {
                language 'en'
                branch 'test branch'
                template 'test template'
                subset 'test subset'
                target new File('test target')
            }
            translation {
                language 'es'
                branch 'test branch'
                template 'test template'
                subset 'test subset'
                target new File('test target')
            }
        }

        Assert.assertEquals 'test name', project.ricotta.projectName
        Assert.assertEquals 'test host', project.ricotta.host

        Assert.assertEquals 2, project.ricotta.translations.size

        Assert.assertEquals 'en', project.ricotta.translations[0].language
        Assert.assertEquals 'es', project.ricotta.translations[1].language

        project.ricotta.translations.each() { RicottaTranslation translation ->
            Assert.assertEquals 'test branch', translation.getBranch()
            Assert.assertEquals 'test template', translation.getTemplate()
            Assert.assertEquals 'test subset', translation.getSubset()
            Assert.assertEquals new File('test target'), translation.getTarget()
        }

        Assert.assertEquals(['en', 'es'], project.ricotta.resourceConfig)
    }
}
