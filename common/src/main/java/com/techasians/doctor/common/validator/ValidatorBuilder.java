package com.techasians.doctor.common.validator;







import com.techasians.doctor.common.validator.factory.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;

public class ValidatorBuilder {

    List<ValidatorGroup> groups = new ArrayList<>();

    public ValidatorBuilder push(ValidatorGroup group) {
        this.groups.add(group);
        return this;
    }


    public List<ValidatorMessage> process() {
        List<ValidatorMessage> messages = new ArrayList<>();
        for(ValidatorGroup group: this.groups) {
            for (ValidateObject object : group.getGroups()) {
                ValidatorService service = ValidatorFactory.getInstant(object.getValidatorGroup());
                if (service != null) {
                    ValidatorMessage message = service.process(object);
                    if (message != null) {
                        messages.add(message);
                    }
                }
            }
        }
        return messages;
    }


    public static void main(String[] args) {
       ValidatorBuilder marketValidate = new ValidatorBuilder();
        ValidatorGroup market = new ValidatorGroup();
        market.ofFieldName("code")
                .ofValue(123)
                .ofRequired()
//                .ofRegex(RegexContant.CODE_REGEX)
                .ofMaxLength(20)
                .ofMinLength(10)
                .ofMaxValue(12313213321d)
                .ofMinValue(123456)
                .ofRequired();
        marketValidate.push(market);

        ValidatorGroup market2 = new ValidatorGroup();
        market2.ofFieldName("name")
                .ofValue(null)
                .ofRequired()
                .ofMaxLength(11)
                .ofMinLength(10);
//        marketValidate.push(market2);

        List<ValidatorMessage> messages = marketValidate.process();

//        System.out.println(messages.get(0).);


    }

}
