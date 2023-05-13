package org.example.common.mailreqex;

import org.example.common.mailreqex.vo.MailMergeConfig;
import org.example.common.mailreqex.vo.MailMergeData;

public interface MailMergeDataBuilder {
    public MailMergeData createMergeData(MailMergeConfig mailMergeConfig);
}
