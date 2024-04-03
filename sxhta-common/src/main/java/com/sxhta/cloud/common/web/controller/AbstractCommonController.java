package com.sxhta.cloud.common.web.controller;

import java.io.Serializable;

public abstract class AbstractCommonController<Search extends Serializable, Request extends Serializable, Response extends Serializable>
        implements ICommonController<Search, Request, Response>{

}
