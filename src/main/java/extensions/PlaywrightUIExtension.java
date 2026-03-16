package extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ViewportSize;
import modules.GuiceComponentModule;
import modules.GuiceModule;
import modules.GuicePageModule;
import org.junit.jupiter.api.extension.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PlaywrightUIExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    @Override
    public void beforeAll(ExtensionContext context) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();

        Injector injector = Guice.createInjector(new GuiceModule(page, browser));
        context.getStore(ExtensionContext.Namespace.GLOBAL).put("injector", injector);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(new ViewportSize(1280, 720))
                .setUserAgent("Mozilla/5.0 (Linux; U; Android 4.0.3; en-us; KFTT Build/IML74K) AppleWebKit/537.36 (KHTML, like Gecko) Silk/3.68 like Chrome/39.0.2171.93 Safari/537.36"));

        page = browserContext.newPage();
        Guice.createInjector(new GuiceModule(page, browser),
                        new GuicePageModule(page),
                        new GuiceComponentModule(page))
                .injectMembers(context.getTestInstance().get());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        try {
            saveTrace(browser, page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        page.close();
        browser.close();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (page != null && !page.isClosed()) {
            page.close();
        }
        if (browser != null && !browser.isConnected()) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }


    public void saveTrace(Browser browser, Page page) throws IOException {
        browser.startTracing(page);
        byte[] trace = browser.stopTracing();
        Path outputDir = Paths.get("trace");
        Files.createDirectories(outputDir);
        Path outputFile = outputDir.resolve("lesson_test_trace.zip");
        Files.write(outputFile, trace, StandardOpenOption.CREATE);
    }
}