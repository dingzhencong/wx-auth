package com.weixin.wx_auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ding.common.JsonObject;
import com.ding.common.message.MessageService;
import com.ding.common.message.enums.MessageEnum;
import com.ding.common.message.enums.MessageStatusEnum;

import com.weixin.wx_auth.common.config.DingXinParamConfig;
import com.weixin.wx_auth.common.config.ELinkParamConfig;
import com.weixin.wx_auth.common.pojo.dingxin.res.BusinessSupBaseRes;
import com.weixin.wx_auth.common.pojo.dingxin.res.UserInfoRes;
import com.weixin.wx_auth.feign.FeignService;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
@Slf4j
public class Test {
    @Autowired
    private RedisTemplate<Object, Object> template;

    public static void main(String[] args) {
        String name = Base64Utils.encodeToString("test_luru".getBytes());
        System.out.println(name);
    }
    @Autowired
    private ELinkParamConfig elinkparamconfig;

    @GetMapping("test")
    @ResponseBody
    public JsonObject test() {
        template.opsForValue().set("DZC",
                JsonObject.builder().code(123).message("code").build());
        template.opsForValue().set("DZC2", "hashValue");
        template.opsForHash().put("key", "hashKey", "hashValue");
        template.opsForHash().put("key", "hashKey2", "收到");
        JsonObject wxUserInfo = (JsonObject) template.opsForValue().get("DZC");
        log.info("user2::{}", wxUserInfo);
        return wxUserInfo;
    }

    @Autowired
    private DingXinParamConfig dingXinParamConfig;

    @GetMapping(value = "/page")
    public Object page2() throws URISyntaxException {
        FeignService service = Feign.builder()
                .target(FeignService.class, dingXinParamConfig.getUserInfoUrl());
        Map paramMap = new HashMap();
        String userid = "9A73241EFDDD4EEC843C4C6B5A5259FB";
        paramMap.put("userId", userid);
        String userInfoString = service.requestByGet(paramMap);
        log.info("userInfoString::{}", userInfoString);
        BusinessSupBaseRes<UserInfoRes> userInfoResBusinessSupBaseRes = JSONObject.parseObject(
                userInfoString, new TypeReference<BusinessSupBaseRes<UserInfoRes>>(){});
        log.info("employeeName::{}", userInfoResBusinessSupBaseRes.getResponseData().getEmployeeName());
        return userInfoResBusinessSupBaseRes;
    }

    @GetMapping(value = "/generateMessageCode")
    public String generateMessageCode(String phone, String text) throws URISyntaxException {
        // 调用方式
        System.out.println(phone);
        String s = "";
        while (s.length() < 6) {
            s += (int) (Math.random() * 10);
        }
        System.out.println(String.format(text, s));
        return "123456";
    }

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/send")
    public String send(String phone) {
        MessageStatusEnum messageStatusEnum = messageService.send(phone, MessageEnum.H5_LOGIN);
        return messageStatusEnum.getDesc();
    }
    @GetMapping(value = "/validate")
    public String validate(String phone,String code) {
        MessageStatusEnum messageStatusEnum = messageService.validate(phone, code, MessageEnum.H5_LOGIN);
        return messageStatusEnum.getDesc();
    }



    /**
     * Definition for singly-linked list.
     *
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
      }

    /*
     * 1.首先，让我们在任一位置 i 将 A(长度为m) 划分成两个部分：
     *            leftA            |                rightA
     *   A[0],A[1],...      A[i-1] |  A[i],A[i+1],...A[m - 1]
     *
     * 由于A有m个元素，所以有m + 1中划分方式(i = 0 ~ m)
     *
     * 我们知道len(leftA) = i, len(rightA) = m - i;
     * 注意：当i = 0时，leftA是空集，而当i = m时，rightA为空集。
     *
     * 2.采用同样的方式，将B也划分为两部分：
     *            leftB            |                rightB
     *   B[0],B[1],...      B[j-1] |   B[j],B[j+1],...B[n - 1]
     *  我们知道len(leftA) = j, len(rightA) = n - j;
     *
     *  将leftA和leftB放入一个集合，将rightA和rightB放入一个集合。再把这两个集合分别命名为leftPart和rightPart。
     *
     *            leftPart         |                rightPart
     *   A[0],A[1],...      A[i-1] |  A[i],A[i+1],...A[m - 1]
     *   B[0],B[1],...      B[j-1] |  B[j],B[j+1],...B[n - 1]
     *
     *   如果我们可以确认：
     *   1.len(leftPart) = len(rightPart); =====> 该条件在m+n为奇数时，该推理不成立
     *   2.max(leftPart) <= min(rightPart);
     *
     *   median = (max(leftPart) + min(rightPart)) / 2;  目标结果
     *
     *   要确保这两个条件满足：
     *   1.i + j = m - i + n - j(或m - i + n - j + 1)  如果n >= m。只需要使i = 0 ~ m，j = (m+n+1)/2-i =====> 该条件在m+n为奇数/偶数时，该推理都成立
     *   2.B[j] >= A[i-1] 并且 A[i] >= B[j-1]
     *
     *   注意:
     *   1.临界条件：i=0,j=0,i=m,j=n。需要考虑
     *   2.为什么n >= m ? 由于0 <= i <= m且j = (m+n+1)/2-i,必须确保j不能为负数。
     *
     *   按照以下步骤进行二叉树搜索
     *   1.设imin = 0,imax = m，然后开始在[imin,imax]中进行搜索
     *   2.令i = (imin+imax) / 2, j = (m+n+1)/2-i
     *   3.现在我们有len(leftPart) = len(rightPart)。而我们只会遇到三种情况：
     *
     *      ①.B[j] >= A[i-1] 并且 A[i] >= B[j-1]  满足条件
     *      ②.B[j-1] > A[i]。此时应该把i增大。 即imin = i + 1;
     *      ③.A[i-1] > B[j]。此时应该把i减小。 即imax = i - 1;
     *
     * */

    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A; A = B; B = temp;
            int tmp = m; m = n; n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft;
                if (i == 0) {//A分成的leftA(空集) 和 rightA(A的全部)  所以leftPart = leftA(空集) + leftB,故maxLeft = B[j-1]。
                    maxLeft = B[j - 1];
                } else if (j == 0) { //B分成的leftB(空集) 和 rightB(B的全部)  所以leftPart = leftA + leftB(空集),故maxLeft = A[i-1]。
                    maxLeft = A[i - 1];
                } else { //排除上述两种特殊情况，正常比较
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) { //奇数，中位数正好是maxLeft
                    return maxLeft;
                }
                //偶数
                int minRight;
                if (i == m) {//A分成的leftA(A的全部) 和 rightA(空集)  所以rightPart = rightA(空集) + rightB,故minRight = B[j]。
                    minRight = B[j];
                } else if (j == n) {//B分成的leftB(B的全部) 和 rightB(空集)  所以rightPart = rightA + rightB(空集),故minRight = A[i]。
                    minRight = A[i];
                } else {//排除上述两种特殊情况，正常比较
                    minRight = Math.min(B[j], A[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    //自己的理解
    class Solution {
        public double findMedianSortedArrays(int[] A, int[] B) {
            int la = A.length;//数组 A 的长度
            int lb = B.length;//数组 B 的长度

            if (la > lb) {//如果数组 A 比较长，则交换 A、B 数组
                int[] temp = A;
                A = B;
                B = temp;

                int tempL = la;
                la = lb;
                lb = tempL;
            }

            int aMin = 0;     //A 数组折半查找左边界
            int aMax = la;    //A 数组折半查找右边界

            // halfLen 的作用就是中点坐标，当 A 数组中折半查找向右移动时，B 数组以 halfLen 为相对点向左移动，以保持始终均分
            int halfLen = (la + lb + 1) >> 1;
            //二分查找

            //情况一: A 数组为空，中位数在 B 数组
            //情况二: A 数组较短
            //  1) A 数组元素都较小，中位数在B数组
            //  2) A 数组元素都较大，中位数在B数组
            //  3) A、B 元素大小分布基本相当，中位数为被分割的两数组左半部分较大的那一个和右半部分较小的那一个之和的一半
            //情况三: A、B 等长
            //  1) A 数组元素都比B数组元素小，中位数为 A 数组尾元素和B数组首元素之和的一半
            //  2) B 数组元素都比A数组元素小，中位数为 B 数组尾元素和A数组首元素之和的一半
            //  3) A、B 元素大小分布基本相当，中位数为被分割的两数组左半部分较大的那一个和右半部分较小的那一个之和的一半
            while (aMin <= aMax) {
                int aIndex = (aMin + aMax) >> 1;  //A数组中分割点
                int bIndex = halfLen - aIndex;  //B数组中分割点

                //数组 A 分割点相邻左边那个元素比数组 B 分割点相邻右边那个元素大，则应该将数组 A 分割点向右移，数组 B 分割点向左移
                //数组 A 分割点有向左移趋势，需检查左边界
                if (aIndex > aMin && A[aIndex - 1] > B[bIndex]) {
                    aMax = aIndex - 1;
                    //数组 A 分割点相邻右边那个元素比数组 B 分割点相邻左边那个元素大，则应该将数组 A 分割点向左移，数组 B 分割点向右移
                    //数组 A 分割点有向右移趋势，需检查右边界
                } else if (aIndex < aMax && B[bIndex - 1] > A[aIndex]) {
                    aMin = aIndex + 1;
                    //得出结果
                } else {

                    int leftPart = 0;
                    //情况一,情况二2，情况三2
                    if (aIndex == 0) {
                        leftPart = B[bIndex-1];
                        //情况三1
                    } else if (bIndex == 0) {
                        leftPart = A[la - 1];
                        //情况二1,情况二3,情况三3
                    } else {
                        leftPart = Math.max(A[aIndex - 1], B[bIndex-1]);
                    }

                    //元素个数总和为奇数
                    if ((la + lb) % 2 == 1) {
                        return leftPart;
                    }

                    //情况一: A 数组为空，中位数在 B 数组
                    //情况二: A 数组较短
                    //  1) A 数组元素都较小，中位数在B数组
                    //  2) A 数组元素都较大，中位数在B数组
                    //  3) A、B 元素大小分布基本相当，中位数为被分割的两数组左半部分较大的那一个和右半部分较小的那一个之和的一半
                    //情况三: A、B 等长
                    //  1) A 数组元素都比B数组元素小，中位数为 A 数组尾元素和B数组首元素之和的一半
                    //  2) B 数组元素都比A数组元素小，中位数为 B 数组尾元素和A数组首元素之和的一半
                    //  3) A、B 元素大小分布基本相当，中位数为被分割的两数组左半部分较大的那一个和右半部分较小的那一个之和的一半

                    //元素个数总和为偶数
                    int rightPart = 0;
                    //情况一,情况二1
                    if (aIndex == la) {
                        rightPart = B[bIndex];
                        //情况三2
                    } else if (bIndex == lb) {
                        rightPart = A[0];
                        //情况二2、3，情况三1、3
                    }else {
                        rightPart = Math.min(A[aIndex], B[bIndex]);
                    }
                    return (leftPart + rightPart) / 2.0;
                }

            }
            return 0;
        }
    }
    public String longestPalindrome(String s) {
        int length = s.length();
        boolean[][] P = new boolean[length][length];
        int maxLen = 0;
        String maxPal = "";
        for (int len = 1; len <= length; len++) //遍历所有的长度
        {
            for (int start = 0; start < length; start++) {
                int end = start + len - 1;
                if (end >= length) //下标已经越界，结束本次循环
                {
                    break;
                }
                P[start][end] = (len == 1 || len == 2 || P[start + 1][end - 1]) && s.charAt(start) == s.charAt(end); //长度为 1 和 2 的单独判断下
                if (P[start][end] && len > maxLen) {
                    maxPal = s.substring(start, end + 1);
                }
            }
        }
        return maxPal;
    }

}
