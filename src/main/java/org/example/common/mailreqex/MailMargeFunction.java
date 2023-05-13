package org.example.common.mailreqex;

import org.example.common.mailreqex.vo.MailMergeData;

@FunctionalInterface
public interface MailMargeFunction {
    MailMergeData init(MailMergeData mailMergeData);
}
