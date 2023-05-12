package org.example.common.mailreqex;

import org.apache.poi.ss.usermodel.Row;
import org.example.common.mailreqex.vo.MailGroupData;

@FunctionalInterface
public interface MailMargeFunction {
    MailGroupData init(MailGroupData mailGroupData);
}
