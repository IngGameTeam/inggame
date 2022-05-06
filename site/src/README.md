Monorepo Example
================

This is an example project showing Doctave documentation inline
in the same directory as source code - as one would in a monorepo.

There are two folders under the `src` directory:

* `backend`
* `frontend`

Both contain a `README.md` file, which will get rendered in
GitHub when viewing a specific directory in the UI. Thanks to
the frontmatter, they will also appear nicely in the Doctave
generated documentation navigation.

The navigation can further be customized in the `doctave.yaml`.

## Usage

To try out this example, clone the repo, [install
Doctave](https://cli.doctave.com/installing), and run `doctave
serve` in the root directory.
