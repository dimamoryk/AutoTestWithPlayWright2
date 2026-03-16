package components;

import com.microsoft.playwright.Page;
import commons.AbsCommon;

public class CommonComponents extends AbsCommon {

    protected Page page;

    public CommonComponents(Page page) {
        this.page = page;
    }

    public void waitForPageLoad() {
        page.waitForLoadState();
    }

}
