package org.cloudbus.cloudsim.examples.AESandDES;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;

import javax.crypto.SecretKey;
import java.util.*;

public class DesSim {
    public static void main(String[] args) {
        try {
            CloudSim.init(1, Calendar.getInstance(), false);
            Datacenter datacenter = createDatacenter("Datacenter_DES");
            DatacenterBroker broker = new DatacenterBroker("Broker");

            int brokerId = broker.getId();
            List<Vm> vmList = List.of(
                    new Vm(0, brokerId, 1000, 1, 512, 1000, 10000, "Xen", new CloudletSchedulerTimeShared()),
                    new Vm(1, brokerId, 1000, 1, 512, 1000, 10000, "Xen", new CloudletSchedulerTimeShared())
            );

            broker.submitVmList(vmList);

            String originalData = "This is secret data between VM1 and VM2";
            SecretKey key = DesUtils.generateDESKey();

            long start = System.currentTimeMillis();
            String encrypted = DesUtils.encryptDES(originalData, key);
            String decrypted = DesUtils.decryptDES(encrypted, key);
            long duration = System.currentTimeMillis() - start;

            long mi = duration * 1000;

            UtilizationModel utilization = new UtilizationModelFull();
            Cloudlet cloudlet = new Cloudlet(0, 40000 + mi, 1, 300, 300, utilization, utilization, utilization);
            cloudlet.setUserId(brokerId);
            cloudlet.setVmId(0);

            broker.submitCloudletList(Collections.singletonList(cloudlet));

            CloudSim.startSimulation();
            CloudSim.stopSimulation();

            printResults(broker.getCloudletReceivedList());

            System.out.println("\nDES Encryption Time: " + duration + " ms");
            System.out.println("Encrypted: " + encrypted);
            System.out.println("Decrypted: " + decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Datacenter createDatacenter(String name) throws Exception {
        List<Pe> peList = List.of(new Pe(0, new PeProvisionerSimple(1000)));
        Host host = new Host(0, new RamProvisionerSimple(2048), new BwProvisionerSimple(10000),
                1000000, peList, new VmSchedulerTimeShared(peList));

        List<Host> hostList = List.of(host);

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                "x86", "Linux", "Xen", hostList,
                10.0, 3.0, 0.05, 0.001, 0.0);

        return new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), new LinkedList<>(), 0);
    }

    private static void printResults(List<Cloudlet> list) {
        String indent = "    ";
        System.out.println("\n========== OUTPUT ==========");
        System.out.println("Cloudlet ID" + indent + "STATUS" + indent + "Datacenter ID" +
                indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

        for (Cloudlet cl : list) {
            System.out.print(indent + cl.getCloudletId() + indent);
            if (cl.getStatus() == Cloudlet.SUCCESS) {
                System.out.println("SUCCESS" + indent + cl.getResourceId() + indent + cl.getVmId() +
                        indent + cl.getActualCPUTime() + indent + cl.getExecStartTime() + indent + cl.getFinishTime());
            }
        }
    }
}
