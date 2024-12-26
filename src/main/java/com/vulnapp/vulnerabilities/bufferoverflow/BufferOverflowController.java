package com.vulnapp.vulnerabilities.bufferoverflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BufferOverflowController {

    /**
     * 메모리 버퍼 오버플로우 설명 페이지 매핑
     * @return templates/bufferoverflow/index.html
     */
    @GetMapping("/vulnerable/bufferoverflow")
    public String showBufferOverflowPage() {
        return "bufferoverflow/index"; // HTML 페이지 렌더링
    }
}
