package org.notatoaster.whiskers.nameservice;


import sun.net.spi.nameservice.NameService;
import sun.net.spi.nameservice.NameServiceDescriptor;

public class ControlledNameServiceDescriptor implements NameServiceDescriptor {
    @Override
    public NameService createNameService() throws Exception {
        return new ControlledNameService();
    }

    @Override
    public String getProviderName() {
        return "foo";
    }

    @Override
    public String getType() {
        return "dns";
    }

}
