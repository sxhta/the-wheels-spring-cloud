package com.sxhta.cloud.gateway.service.impl;

import com.google.code.kaptcha.Producer;
import com.sxhta.cloud.cache.redis.service.RedisService;
import com.sxhta.cloud.common.constant.CacheConstants;
import com.sxhta.cloud.common.constant.Constants;
import com.sxhta.cloud.common.exception.CaptchaException;
import com.sxhta.cloud.common.utils.sign.Base64;
import com.sxhta.cloud.common.utils.uuid.IdUtils;
import com.sxhta.cloud.common.web.domain.CommonResponse;
import com.sxhta.cloud.gateway.config.properties.CaptchaProperties;
import com.sxhta.cloud.gateway.response.CaptchaResponse;
import com.sxhta.cloud.gateway.service.ValidateCodeService;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.util.concurrent.TimeUnit;

/**
 * 验证码实现处理
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 这里根据 Name获取，必须用 @Resource
     */
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Inject
    private RedisService<String, String> redisService;

    @Inject
    private CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     */
    @Override
    public CommonResponse createCaptcha() throws CaptchaException {
        final var captchaEnabled = captchaProperties.getEnabled();
        final var response = new CaptchaResponse();
        response.setCaptchaEnabled(captchaEnabled);
        if (!captchaEnabled) {
            return CommonResponse.success();
        }
        // 保存验证码信息
        final var uuid = IdUtils.simpleUUID();
        final var verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr;
        String code = null;
        BufferedImage image = null;

        final var captchaType = captchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType)) {
            final var capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        final var os = new FastByteArrayOutputStream();
        try {
            if (image != null) {
                ImageIO.write(image, "jpg", os);
            }
        } catch (IOException e) {
            return CommonResponse.error(e.getMessage());
        }

        final var img = Base64.encode(os.toByteArray());
        response.setUuid(uuid)
                .setImg(img);
        return CommonResponse.success(response);
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaException {
        if (StringUtils.isEmpty(code)) {
            throw new CaptchaException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new CaptchaException("验证码已失效");
        }
        final var verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        final String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException("验证码错误");
        }
    }
}
