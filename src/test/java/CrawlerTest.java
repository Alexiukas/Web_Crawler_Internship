import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrawlerTest {

    String text = "Today is a good day because it is sunny outside and it is nice";

    @Test
    void howManyPhrasesFoundInText() {
        String phrase = "is";
        String count = String.valueOf(StringUtils.countMatches(text.toLowerCase(), phrase.toLowerCase()));
        assertEquals("3", count);
    }


}