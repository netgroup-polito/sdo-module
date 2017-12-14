# sdo-module

This software implements a Self-orchestrating module for SDO architecture.

Dependencies:

- The Universal Node with KVM/libvirt support enabled at commit 6b402961761d0fd21e1f83d88df597481463ffc4 with:
	- Compile with KVM/libvirt support, Debug level set to ORCH_DEBUG (not ORCH_DEBUG_INFO) and enable log to file 
	- The patch un.patch applied
	- Install jq and libguestmount (libguestfs-tools on Ubuntu for the command 'guestmount')
	- The file add_datadisk_files.sh placed in un-orchestrator/orchestrator/compute-node/plugins/kvm-libvirt/scripts/add_datadisk_files.sh and make executable
- The GUI at commit bcd00c5b98db463e99adf525b4a4a095099fdcb8
- The datastore at commit d68d9ab4489ef7485d706f4f48c6ab7c67f384af
- The Universal Node SDO ResourceManager
- The Datastore
- The Configuration Service at commit a89f26f64cb22c11f8bb149dfc386f1a351a5b94 with the patch ConfigurationService.patch applied
- haveged daemon installed and running on the host
- guestmount util installed
