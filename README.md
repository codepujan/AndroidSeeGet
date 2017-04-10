# Using Git and Making Pull Requests

1. Always keep `master` branch up to date. Do this by running `git pull --rebase origin master` on the `master` branch every day.

2. When starting new work, always create a new branch and do your work there. You can do this by running the command `git checkout -b [a-hyphen-seperated-branch-name]` where you name the branch something related to what you are working on, seperated by hyphens.

3. Once you are on your new branch, you can start coding and making commits. The general rule of thumb is to make small commits, and write meaningful commit messages. A commit should correspond to a specific change, so do not group a bunch of non-related changes into a single commit. You can push these changes to your branch anytime via the command `git push origin [branch-name]`.

4. Once you are done with your changes, and feel you are ready to submit it as a Pull Request, make sure all the changes in your branch have been pushed. If they are, go to the SeeGet repo on GitHub, and create a Pull Request for the branch. 
Assign everyone else working on the project as "Reviewers". Once you have assigned the "Reviewers", you can go ahead and submit the Pull Request. No further action is needed.

5. After the Pull Request has been submitted, the reviewers will need to look at your changes, and will make comments if there are any changes that should be modified or improved. Your job is to fix those changes. Once all changes have been made, and your branch is approved to be merged, either a "Reviewer" or you, may merge the branch into `master`. 

6. To merge a Pull Request into `master`, please use the `Merge and Rebase` option. Otherwise, merge conflicts may occur.