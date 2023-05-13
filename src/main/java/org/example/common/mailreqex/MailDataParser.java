package org.example.common.mailreqex;

import org.example.common.mailreqex.vo.MailMergeConfig;
import org.example.common.mailreqex.vo.MailMergeFindKey;
import org.example.common.mailreqex.vo.MailParingData;

import java.util.List;

public class MailDataParser {
    private MailParingData mailParingData;
    private MailMergeConfig config;

    private MailMergeDataBuilder mailMergeDataBuilder;

    public MailDataParser(String originalHtml) throws Exception {
        this.mailParingData = MailReqEx.parseConfigLine(originalHtml);
        this.config = mailParingData.getMailMargeConfig();
    }

    public MailMergeConfig getConfig() {
        return this.config;
    }

    public String parse(MailMergeDataBuilder mailMergeDataBuilder) throws Exception {
        List<MailMergeFindKey> parseKeys = config.getMailMergeFindKeys();
        if (parseKeys.size() > 0) {
            return MailReqEx.parse(mailParingData.getMailHtml(),mailMergeDataBuilder.createMergeData(config));
        } else {
            return mailParingData.getOriginalHtml();
        }
    }
}
