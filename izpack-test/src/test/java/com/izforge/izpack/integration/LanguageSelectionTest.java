package com.izforge.izpack.integration;

import com.izforge.izpack.api.GuiId;
import com.izforge.izpack.api.data.ResourceManager;
import com.izforge.izpack.installer.container.IInstallerContainer;
import com.izforge.izpack.installer.language.LanguageDialog;
import org.fest.swing.exception.ScreenLockException;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for an installation using mock data
 */
public class LanguageSelectionTest extends AbstractInstallationTest {

    @After
    public void tearBinding() {
        applicationContainer.dispose();
        try {
            if (dialogFrameFixture != null) {
                dialogFrameFixture.cleanUp();
                dialogFrameFixture = null;
            }
        } catch (ScreenLockException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void langpackFraShouldBeSet() throws Exception {
        compileAndUnzip("fraInstaller.xml", getWorkingDirectory("samples"));
        installerContainer = applicationContainer.getComponent(IInstallerContainer.class);
        installerContainer.getComponent(LanguageDialog.class).initLangPack();
        ResourceManager resourceManager = applicationContainer.getComponent(ResourceManager.class);
        assertThat(resourceManager.getLocale(), Is.is("fra"));
    }

    @Test
    public void testLangPickerChoseFra() throws Exception {
        compileAndUnzip("basicInstall.xml", getWorkingDirectory("samples/basicInstall"));
        installerContainer = applicationContainer.getComponent(IInstallerContainer.class);
        dialogFrameFixture = prepareDialogFixture();
        assertThat(dialogFrameFixture.comboBox(GuiId.COMBO_BOX_LANG_FLAG.id).contents(), Is.is(new String[]{"eng", "fra"}));
        dialogFrameFixture.comboBox(GuiId.COMBO_BOX_LANG_FLAG.id).selectItem(1);
        dialogFrameFixture.button(GuiId.BUTTON_LANG_OK.id).click();
        ResourceManager resourceManager = applicationContainer.getComponent(ResourceManager.class);
        assertThat(resourceManager.getLocale(), Is.is("fra"));
    }


}